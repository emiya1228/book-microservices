package com.book.booksearch.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host:127.0.0.1}")
    private String redisHost;

    @Value("${spring.redis.port:6379}")
    private int redisPort;

    @Value("${spring.redis.password:}")
    private String redisPassword;

    @Value("${spring.redis.database:0}")
    private int database;

    @Value("${spring.redis.timeout:3000}")
    private int timeout;

    /**
     * 单机模式 Redisson 配置
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();

        // 使用单机模式
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%d", redisHost, redisPort))
                .setPassword(redisPassword.isEmpty() ? null : redisPassword)
                .setDatabase(database)
                .setConnectTimeout(timeout)
                .setTimeout(timeout)
                .setRetryAttempts(3)
                .setRetryInterval(1500)
                .setConnectionPoolSize(64)
                .setConnectionMinimumIdleSize(10)
                .setSubscriptionConnectionPoolSize(50)
                .setSubscriptionsPerConnection(5)
                .setClientName("redisson-client");

        // 设置编码器（默认是JsonJacksonCodec）
        config.setCodec(new org.redisson.codec.JsonJacksonCodec());

        // 设置线程数
        config.setThreads(16);
        config.setNettyThreads(32);

        return Redisson.create(config);
    }
}