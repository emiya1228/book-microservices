package com.book.booksearch.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class InternalCallInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        System.out.println("🚀 Feign拦截器被触发！添加内部调用标识...");

        // 添加内部调用标识
        template.header("X-Internal-Call", "true");

    }
}
