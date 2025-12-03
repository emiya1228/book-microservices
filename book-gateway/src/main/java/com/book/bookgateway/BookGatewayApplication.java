package com.book.bookgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class}  // 排除数据源配置
)
@EnableDiscoveryClient
public class BookGatewayApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookGatewayApplication.class, args);
    }

}
