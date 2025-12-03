package com.book.bookcore.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Book Core Service!";
    }

    @GetMapping("/info")
    public String getInfo() {
        return "服务信息：端口8081，服务名：book-core-service";
    }
}
