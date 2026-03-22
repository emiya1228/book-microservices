package com.book.booksearch.business;

import com.book.bookcommon.dto.BookDTO;
import com.book.bookcommon.result.Response;
import com.book.booksearch.entity.BookDocument;
import com.book.booksearch.rpc.CoreRpc;
import com.book.booksearch.service.BookDocumentService;
import com.book.booksearch.utils.BookDocumentConverter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class SimpleScheduler {

    @Resource
    private BookDocumentService bookDocumentService;
    @Resource
    private CoreRpc coreRpc;
    @Resource
    private BookDocumentConverter bookDocumentConverter;
    @Resource
    private RedissonClient redissonClient;
    private static final String REDIS_LOCK_KEY = "quartz:job:syncData:lock";

    @Scheduled(cron = "0 0 22 * * ?")
    public void scheduled() {
        RLock lock = redissonClient.getLock(REDIS_LOCK_KEY);
        // 尝试获取锁，等待最多10秒，锁持有30秒后自动释放
        boolean locked = false;
        try {
            locked = lock.tryLock(10, 30, TimeUnit.SECONDS);

            if (locked) {
                System.out.println("获取分布式锁成功，开始执行任务");
                Response<List<BookDTO>>  today = coreRpc.getToday();
                List<BookDTO> data = today.getData();
                if (data != null && data.size() > 0) {
                    List<BookDocument> save = new ArrayList<>();
                    for(BookDTO dto : data) {
                        BookDocument bookDocument = bookDocumentConverter.convertToDocument(dto);
                        save.add(bookDocument);
                    }
                    bookDocumentService.updateBatch(save);
                }
            } else {
                System.out.println("获取分布式锁失败，其他实例正在执行");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("获取锁被中断");
        } finally {
            if (locked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
