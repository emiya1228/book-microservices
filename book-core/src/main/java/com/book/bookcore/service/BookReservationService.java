package com.book.bookcore.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.bookcommon.config.UserContext;
import com.book.bookcommon.constant.Constants;
import com.book.bookcommon.exception.ServiceException;
import com.book.bookcore.entity.BookReservation;
import com.book.bookcore.mapper.BookReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class BookReservationService {
    @Resource
    private BookReservationMapper bookReservationMapper;
    @Resource
    private BookService bookService;

    public BookReservation getBookReservation(Long bookId, Long userId, Integer status) {
        BookReservation bookReservation = bookReservationMapper.selectOne(new QueryWrapper<BookReservation>().eq("book_id", bookId).eq("user_id", userId).eq("status", status));
        if (bookReservation == null) {
            throw new ServiceException("查询无返回记录!", 6002);
        }
        return bookReservation;
    }

    @Transactional(rollbackFor = Exception.class)
    public BookReservation addBookReservation(BookReservation bookReservation) {
        if(!bookService.isExistBookNotAvailable(bookReservation.getBookId())){
            throw new ServiceException("图书不存在或仍有库存", 6001);
        }
        Long userId = UserContext.getCurrentUserId();
        BookReservation check = bookReservationMapper.selectOne(new QueryWrapper<BookReservation>().eq("book_id", bookReservation.getBookId()).eq("user_id", userId).eq("status", Constants.RESERVE_STATUS_WAIT));
        if (check != null) {
            throw new ServiceException("已有相同预约记录!", 6003);
        }
        bookReservation.setUserId(userId);
        bookReservation.setCreateTime(LocalDateTime.now());
        bookReservationMapper.insert(bookReservation);
        return bookReservation;
    }
}
