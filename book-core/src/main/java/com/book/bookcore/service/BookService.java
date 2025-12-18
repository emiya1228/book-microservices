package com.book.bookcore.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.bookcommon.exception.ServiceException;
import com.book.bookcore.dto.BookCreateDTO;
import com.book.bookcore.dto.BookUpdateDTO;
import com.book.bookcore.entity.*;
import com.book.bookcore.mapper.*;
import com.book.bookcore.mq.BookMqProducer;
import com.book.bookcore.util.BookUtil;
import com.book.bookcore.vo.BookDetailVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private BookTagRelationMapper bookTagRelationMapper;
    @Resource
    private BookImageMapper bookImageMapper;
    @Resource
    private BookCategoryMapper bookCategoryMapper;
    @Resource
    private BookTagMapper bookTagMapper;
    @Resource
    private BookMqProducer bookMqProducer;

    @Transactional(rollbackFor = Exception.class)
    public Long addBook(BookCreateDTO bookCreateDTO) {
        BookCategory bookCategory = bookCategoryMapper.selectById(bookCreateDTO.getCategoryId());
        if (bookCategory == null) {
            throw new ServiceException("图书类别不存在！", 2001);
        }
        Book book = BookUtil.createDTO2Book(bookCreateDTO);
        bookMapper.insert(book);
        if (bookCreateDTO.getTagIds() != null && bookCreateDTO.getTagIds().isEmpty()) {
            List<BookTagRelation> bookTagRelations = BookUtil.tagIds2BookTagRelations(bookCreateDTO.getTagIds(), book.getId());
            bookTagRelationMapper.batchInsert(bookTagRelations);
        }
        if (bookCreateDTO.getImages() != null && bookCreateDTO.getImages().size() > 0) {
            List<BookImage> bookImages = BookUtil.images2BookImages(bookCreateDTO.getImages(), book.getId());
            bookImageMapper.batchInsert(bookImages);
        }
        bookMqProducer.sendBookAddMessage(book.getId(), book);
        return book.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateBook(BookUpdateDTO bookUpdateDTO) {
        Book book = BookUtil.updateDTO2Book(bookUpdateDTO);
        bookMapper.updateById(book);
        if (bookUpdateDTO.getTagIds() != null && bookUpdateDTO.getTagIds().isEmpty()) {
            List<BookTagRelation> bookTagRelations = BookUtil.tagIds2BookTagRelations(bookUpdateDTO.getTagIds(), book.getId());
            for (BookTagRelation bookTagRelation : bookTagRelations) {
                bookTagRelationMapper.updateById(bookTagRelation);
            }
        }
        if (bookUpdateDTO.getImages() != null && bookUpdateDTO.getImages().size() > 0) {
            List<BookImage> bookImages = BookUtil.imagesup2BookImages(bookUpdateDTO.getImages(), book.getId());
            for (BookImage bookImage : bookImages) {
                bookImageMapper.updateById(bookImage);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteBook(Long id) {
        int i = bookMapper.deleteById(id);
        int i1 = bookTagRelationMapper.deleteByBookId(id);
        int i2 = bookImageMapper.deleteByBookId(id);
        if (i <= 0 || i1 <= 0 || i2 <= 0) {
            throw new ServiceException("删除失败", 2003);
        }
    }


    public BookDetailVO getBookDetail(Long id) {
        Book book = bookMapper.selectById(id);
        if (book == null) {
            throw new ServiceException("图书不存在", 2004);
        }
        BookDetailVO bookDetailVO = new BookDetailVO();
        bookDetailVO.setId(book.getId());
        bookDetailVO.setTitle(book.getTitle());
        bookDetailVO.setAuthor(book.getAuthor());
        bookDetailVO.setPublisher(book.getPublisher());
        bookDetailVO.setCategoryId(book.getCategoryId());
        bookDetailVO.setDescription(book.getDescription());
        bookDetailVO.setPublishDate(book.getPublishDate());
        bookDetailVO.setLanguage(book.getLanguage());
        bookDetailVO.setPageCount(book.getPageCount());
        bookDetailVO.setCoverImage(book.getCoverImage());
        bookDetailVO.setPrice(book.getPrice());
        List<Long> relations = bookTagRelationMapper.selectTagIdsByBookId(book.getId());
        if (relations != null && relations.size() > 0) {
            QueryWrapper<BookTag> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("tag_id", relations);
            List<BookTag> bookTags = bookTagMapper.selectList(queryWrapper);
            bookDetailVO.setTags(bookTags);
        }
        List<BookImage> bookImages = bookImageMapper.selectByBookId(id);
        bookDetailVO.setImages(bookImages);
        return bookDetailVO;
    }

    public boolean isExistBook(Long id) {
        return bookMapper.exists(new QueryWrapper<Book>().eq("id", id)) ;
    }

    public boolean isExistBookNotAvailable(Long id) {
        return bookMapper.exists(new QueryWrapper<Book>().eq("id", id).eq("stock", 0)) ;
    }

    public int decreaseStock(Long bookId, Integer num) {
        return bookMapper.decreaseStock(bookId, num);
    }

    public int increaseStock(Long bookId, Integer num) {
        return bookMapper.increaseStock(bookId, num);
    }

    public List<Book> recommendBooks (Long userId){
        //获取该用户借阅最多的三种类别
        List<String> categoryByUserId = bookMapper.getCategoryIdsByUserId(userId);
        if (categoryByUserId == null) {
            return new ArrayList<>();
        }
        //获取该用户指定类别里没看过的且库存里还有的最热门的书
        List<Book> recommendBooks = bookMapper.getRecommendBooks(categoryByUserId, userId);
        if (recommendBooks == null) {
            return new ArrayList<>();
        }
        return recommendBooks;
    }

}
