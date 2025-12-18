package com.book.bookcore.Controller;

import com.book.bookcommon.result.Response;
import com.book.bookcore.entity.BookReservation;
import com.book.bookcore.service.BookReservationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/core/reserver")
public class BookReservationController {

    @Resource
    private BookReservationService bookReservationService;

    @PostMapping
    public Response addBookReservation(@RequestBody BookReservation bookReservation) {
        BookReservation bookReservation1 = bookReservationService.addBookReservation(bookReservation);
        return Response.success(bookReservation1);
    }

    @GetMapping
    public Response getBookReservations(@RequestBody BookReservation bookReservation) {
        BookReservation bookReservation1 = bookReservationService.getBookReservation(bookReservation.getBookId(), bookReservation.getUserId(), bookReservation.getStatus());
        return Response.success(bookReservation1);
    }
}
