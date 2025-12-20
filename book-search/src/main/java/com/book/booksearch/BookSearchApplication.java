package com.book.booksearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.book.booksearch.rpc")
@EnableElasticsearchRepositories(basePackages = "com.book.booksearch.repository")
public class BookSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookSearchApplication.class, args);
    }
}
