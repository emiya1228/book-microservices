package com.book.bookcore;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@MapperScan("com.book.bookcore.mapper")
public class BookCoreApplication {

    public static void main(String[] args) {
        // 先检查配置
        checkNacosConfig();
        SpringApplication.run(BookCoreApplication.class, args);
    }

    private static void checkNacosConfig() {
        // 可以在这里添加配置检查逻辑
        System.setProperty("spring.cloud.nacos.config.enable-remote-sync-config", "true");
    }

}
