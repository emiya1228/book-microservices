package com.book.booksearch.rpc;

import com.book.bookcommon.dto.BookDTO;
import com.book.bookcommon.result.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "book-core-service")
public interface  CoreRpc {

    @GetMapping("/api/core/today")
    Response<List<BookDTO>> getToday();
}
