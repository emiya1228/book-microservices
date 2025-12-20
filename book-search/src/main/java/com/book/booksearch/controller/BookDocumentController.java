package com.book.booksearch.controller;

import com.book.bookcommon.result.Response;
import com.book.booksearch.business.SimpleScheduler;
import com.book.booksearch.entity.BookDocument;
import com.book.booksearch.service.BookDocumentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class BookDocumentController {
    @Resource
    private BookDocumentService bookDocumentService;
    @Resource
    private SimpleScheduler simpleScheduler;

    @GetMapping("{bookId}")
    public Response getBookDocument(@PathVariable Long bookId) {
        BookDocument bookDocumentById = bookDocumentService.getBookDocumentById(bookId);
        return Response.success(bookDocumentById);
    }

    @GetMapping("/title/{title}")
    public Response getBookDocumentByTitle(@PathVariable String title) {
        List<BookDocument> bookDocumentsByTitle = bookDocumentService.getBookDocumentsByTitle(title);
        return Response.success(bookDocumentsByTitle);
    }

    @PostMapping
    public Response addBookDocument() {
        simpleScheduler.scheduled();
        return Response.success(null);
    }


}
