package com.book.bookcore.service;

import com.book.bookcommon.constant.Constants;
import com.book.bookcommon.exception.ServiceException;
import com.book.bookcore.entity.BookCategory;
import com.book.bookcore.mapper.BookCategoryMapper;
import com.book.bookcore.vo.CategoryTreeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
public class BookCategoryService {
    @Resource
    private BookCategoryMapper bookCategoryMapper;

    @Transactional
    public void createCategory(BookCategory bookCategory) {
        bookCategoryMapper.insert(bookCategory);
    }

    @Transactional
    public void updateCategory(BookCategory bookCategory) {
        bookCategoryMapper.updateById(bookCategory);
    }
    public List<CategoryTreeVO> getCategoryTree() {
        List<BookCategory> bookCategories = bookCategoryMapper.selectCategoryTree();
        List<CategoryTreeVO> res = new ArrayList<>();
        for (BookCategory bookCategory : bookCategories) {
            CategoryTreeVO categoryTreeVO = new CategoryTreeVO();
            categoryTreeVO.setId(bookCategory.getId());
            categoryTreeVO.setName(bookCategory.getName());
            categoryTreeVO.setParentId(bookCategory.getParentId());
            categoryTreeVO.setStatus(bookCategory.getStatus());
            res.add(categoryTreeVO);
        }
        for (CategoryTreeVO categoryTreeVO : res) {
            for (CategoryTreeVO a : res) {
                if (a.getParentId().equals(categoryTreeVO.getId())) {
                    categoryTreeVO.getChildren().add(a);
                }
            }
        }
        List<CategoryTreeVO> collect = res.stream().sorted(Comparator.comparing(CategoryTreeVO::getId)).collect(Collectors.toList());
        return res;
    }

    public List<BookCategory> getSubCategories(Long parentId) {
        List<BookCategory> bookCategories = bookCategoryMapper.selectByParentId(parentId);
        return bookCategories;
    }
    @Transactional(rollbackFor = Exception.class)
    public void enableCategory(Long id) {
        int res = bookCategoryMapper.updateStatus(id, Constants.CATEGORY_STATUS_NORMAL);
        if (res <= 0) {
            throw new ServiceException("启用失败", 3001);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void disableCategory(Long id) {
        int res = bookCategoryMapper.updateStatus(id, Constants.CATEGORY_STATUS_UNNORMAL);
        if (res <= 0) {
            throw new ServiceException("禁用失败", 3002);
        }
    }
}
