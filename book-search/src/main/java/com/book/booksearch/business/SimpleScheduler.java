package com.book.booksearch.business;

import com.book.bookcommon.dto.BookDTO;
import com.book.bookcommon.result.Response;
import com.book.booksearch.entity.BookDocument;
import com.book.booksearch.rpc.CoreRpc;
import com.book.booksearch.service.BookDocumentService;
import com.book.booksearch.utils.BookDocumentConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SimpleScheduler {

    @Resource
    private BookDocumentService bookDocumentService;
    @Resource
    private CoreRpc coreRpc;
    @Resource
    private BookDocumentConverter bookDocumentConverter;

    //@Scheduled(cron = "0 0 22 * * ?")
    public void scheduled() {
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

    }
}
