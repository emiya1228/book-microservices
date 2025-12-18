package com.book.bookcore.Controller;
import com.book.bookcore.dto.BookCreateDTO;
import com.book.bookcommon.result.Response;
import com.book.bookcore.dto.BookUpdateDTO;
import com.book.bookcore.entity.Book;
import com.book.bookcore.service.BookService;
import com.book.bookcore.vo.BookDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/core")
public class BookController {
    private final BookService bookService;

    /**
     * 创建图书
     */
    @PostMapping("/add")
    public Response createBook(@Valid @RequestBody BookCreateDTO dto) {
        Long bookId = bookService.addBook(dto);
        return Response.success(bookId);
    }

    @PostMapping("/update")
    public Response updateBook(@Valid @RequestBody BookUpdateDTO dto) {
        bookService.updateBook(dto);
        return Response.success(true);
    }

    /**
     * 删除图书
     */
    @DeleteMapping("/{id}")
    public Response deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Response.success(true);
    }

    /**
     * 获取图书详情
     */
    @GetMapping("/{id}")
    public Response getBookDetail(@PathVariable Long id) {
        BookDetailVO bookDetail = bookService.getBookDetail(id);
        return Response.success(bookDetail);
    }

    @GetMapping("/recommend/{id}")
    public Response getRecommendBook(@PathVariable Long id) {
        List<Book> books = bookService.recommendBooks(id);
        return Response.success(books);
    }
}
