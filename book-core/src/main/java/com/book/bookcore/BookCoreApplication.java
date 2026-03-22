package com.book.bookcore;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(RocketMQAutoConfiguration.class)
@MapperScan("com.book.bookcore.mapper")
public class BookCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookCoreApplication.class, args);
    }


}
