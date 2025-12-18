package com.book.bookcore.Controller;

import com.book.bookcommon.config.UserContext;
import com.book.bookcommon.result.Response;
import com.book.bookcore.entity.BorrowRecord;
import com.book.bookcore.service.BorrowService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/core/borrow")
public class BookBorrowController {
    @Resource
    private BorrowService borrowService;

    /**
     * 借阅图书
     */
    @PostMapping("{id}")
    public Response borrowBook(@PathVariable Long id) {
        Long currentUserId = UserContext.getCurrentUserId();
        BorrowRecord borrowRecord = borrowService.borrowBook(currentUserId, id);
        return Response.success(borrowRecord);
    }

    /**
     * 归还图书
     */
    @PutMapping("/return")
    public Response returnBook(@RequestBody @Validated BorrowRecord in) {
        Long currentUserId = UserContext.getCurrentUserId();
        BorrowRecord borrowRecord = borrowService.returnBook(currentUserId, in.getId(), in.getBookId());
        return Response.success(borrowRecord);
    }

    /**
     * 续借图书
     */
    @PutMapping("/{recordId}/renew")
    public Response renewBook(@PathVariable Long recordId) {
        BorrowRecord borrowRecord = borrowService.renewBook(recordId);
        return Response.success(borrowRecord);
    }
}
