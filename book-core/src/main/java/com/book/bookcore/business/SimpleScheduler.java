package com.book.bookcore.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.book.bookcommon.constant.Constants;
import com.book.bookcore.entity.BookReservation;
import com.book.bookcore.entity.BorrowRecord;
import com.book.bookcore.mapper.BookReservationMapper;
import com.book.bookcore.mapper.BorrowRecordMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Component
public class SimpleScheduler {

    @Resource
    private BookReservationMapper bookReservationMapper;
    @Resource
    private BorrowRecordMapper borrowRecordMapper;

    @Scheduled(cron = "0 0 2 * * ?")
    public void dailyTask() {
        List<BorrowRecord> borrowRecords = borrowRecordMapper.selectList(new QueryWrapper<BorrowRecord>().lt("due_date", LocalDate.now()).eq("status", "借阅中"));
        //todo
    }

    // 2. 固定间隔执行
    @Scheduled(fixedRate = 300000) // 每5分钟
    public void fixedRateTask() {
        List<BookReservation> bookReservations = bookReservationMapper.selectReadyReservations();
        if (bookReservations != null) {
            //todo
            for (BookReservation bookReservation : bookReservations) {
                bookReservation.setStatus(Constants.RESERVE_STATUS_CONFIRM);
                bookReservationMapper.update(bookReservation);
            }
        }
    }
}
