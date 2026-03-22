package com.book.bookcore.Controller;

import com.book.bookcommon.result.Response;
import com.book.bookcore.ai.IntelligentSearchService;
import com.book.bookcore.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Resource
    private IntelligentSearchService intelligentSearchService;

    @GetMapping("/{request}")
    public Response search(@PathVariable String request) {
        List<Book> books = intelligentSearchService.queryBooks(request);
        return Response.success(books);
    }
}
