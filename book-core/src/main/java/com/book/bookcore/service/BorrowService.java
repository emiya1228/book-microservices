package com.book.bookcore.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.bookcommon.config.UserContext;
import com.book.bookcommon.constant.Constants;
import com.book.bookcommon.exception.ServiceException;
import com.book.bookcore.entity.BookReservation;
import com.book.bookcore.entity.BorrowRecord;
import com.book.bookcore.entity.Fine;
import com.book.bookcore.mapper.BookReservationMapper;
import com.book.bookcore.mapper.BorrowRecordMapper;
import com.book.bookcore.util.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BorrowService {
    @Resource
    private BorrowRecordMapper borrowRecordMapper;
    @Resource
    private BookService bookService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private FineService fineService;
    @Resource
    private BookReservationMapper bookReservationMapper;

    private final Map<Long, Object> bookLocks = new ConcurrentHashMap<>();


    @Transactional(rollbackFor = Exception.class)
    public BorrowRecord borrowBook(Long userId, Long bookId) {
        if (!bookService.isExistBook(bookId)){
            throw new ServiceException("图书不存在！", 2004);
        }
        if (borrowRecordMapper.exists(new QueryWrapper<BorrowRecord>().eq("user_id", userId).eq("book_id", bookId))) {
            throw new ServiceException("您已经借阅过此图书！", 5001);
        }
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setUserId(userId);
        borrowRecord.setBookId(bookId);

        borrowRecord.setBorrowDate(LocalDate.now());
        // borrow_date使用数据库默认值CURRENT_TIMESTAMP
        borrowRecord.setDueDate(LocalDate.now().plusDays(30));
        // return_date默认为null
        borrowRecord.setStatus(Constants.STATUS_BORROW);
        borrowRecord.setRenewCount(0);

        int insertResult = borrowRecordMapper.insert(borrowRecord);
        if (insertResult <= 0) {
            throw new ServiceException("创建借阅记录失败", 50001);
        }

        // 7. 更新图书库存
        int updateResult = bookService.decreaseStock(bookId, 1);
        if (updateResult <= 0) {
            throw new ServiceException("更新图书库存失败", 50002);
        }
        return borrowRecord;
    }
    @Transactional(rollbackFor = Exception.class)
    public BorrowRecord returnBook(Long userId, Long id, Long bookId) {
        if (!bookService.isExistBook(bookId)){
            throw new ServiceException("图书不存在！", 2004);
        }
        BorrowRecord borrowRecord = borrowRecordMapper.selectById(id);
        if (borrowRecord == null) {
            throw new ServiceException("未查询到此借阅记录", 50003);
        }
        if(!bookId.equals(borrowRecord.getBookId())) {
            throw new ServiceException("书籍信息不匹配，请核对");
        }
        borrowRecord.setStatus("已归还");
        borrowRecord.setReturnDate(LocalDate.now());
        int insertResult =borrowRecordMapper.updateById(borrowRecord);

        if (insertResult <= 0) {
            throw new ServiceException("归还书籍失败", 50001);
        }
        if(bookService.isExistBookNotAvailable(bookId)){
            Object lock = bookLocks.computeIfAbsent(bookId, k -> new Object());
            synchronized (lock) {
                BookReservation bookReservation = bookReservationMapper.selectWaitingReservation(bookId);
                if (bookReservation !=null) {
                    bookReservation.setStatus(Constants.RESERVE_STATUS_READY);
                    bookReservationMapper.updateById(bookReservation);
                }
            }
        }
        // 7. 更新图书库存
        int updateResult = bookService.increaseStock(bookId, 1);
        if (updateResult <= 0) {
            throw new ServiceException("更新图书库存失败", 50002);
        }
        if (borrowRecord.getReturnDate().isAfter(borrowRecord.getDueDate())) {
            Fine fine = new Fine();
            fine.setUserId(userId);
            fine.setBorrowRecordId(id);
            fine.setStatus(Constants.STATUS_UNPAID);
            fine.setCreateTime(LocalDateTime.now());
            fine.setAmount(ChronoUnit.DAYS.between(borrowRecord.getDueDate(), borrowRecord.getReturnDate()));
            fine.setId(redisUtil.incr(Constants.FINE, 1));
            fineService.addFine(fine);
        }
        return borrowRecord;
    }

    @Transactional(rollbackFor = Exception.class)
    public BorrowRecord renewBook(Long id) {
        Long currentUserId = UserContext.getCurrentUserId();
        BorrowRecord borrowRecord = borrowRecordMapper.selectById(id);
        if(borrowRecord == null) {
            throw new ServiceException("未查询到此借阅记录", 50003);
        }
        if(borrowRecord.getUserId() != currentUserId) {
            throw new ServiceException("借阅用户与当前用户不符！", 50004);
        }
        if(borrowRecord.getRenewCount() >= Constants.MAX_RENEW_COUNT) {
            throw new ServiceException("已超过续借次数", 50005);
        }
        borrowRecord.setDueDate(borrowRecord.getDueDate().plusDays(Constants.RENEW_DAYS));
        borrowRecord.setRenewCount(borrowRecord.getRenewCount() + 1);
        borrowRecordMapper.updateById(borrowRecord);
        return borrowRecord;
    }
}
