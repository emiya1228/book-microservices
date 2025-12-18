package com.book.bookuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.book.bookuser.mapper")
public class BookUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookUserApplication.class, args);
    }

}
