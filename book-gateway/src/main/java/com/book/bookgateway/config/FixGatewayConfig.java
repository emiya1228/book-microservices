package com.book.bookgateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebFilter;

@Configuration
public class FixGatewayConfig {

    // 1. 禁用静态资源
    @Bean
    public WebFluxConfigurer webFluxConfigurer() {
        return new WebFluxConfigurer() {
            @Override
            public void addResourceHandlers(org.springframework.web.reactive.config.ResourceHandlerRegistry registry) {
                // 空实现，不注册任何静态资源
            }
        };
    }

    // 2. 优先处理API请求的过滤器
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebFilter apiPreFilter() {
        return (exchange, chain) -> {
            exchange.getAttributes().put("skipStaticResources", true);
            return chain.filter(exchange);
        };
    }
}
