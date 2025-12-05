package com.book.bookgateway.filter;

import com.book.bookgateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;


import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {


    private JwtUtil jwtUtil;
    private final PathMatcher pathMatcher = new AntPathMatcher();


    private List<String> whiteList= Arrays.asList(
            "/api/user/login",
            "/api/user/register"
    );

    @Data
    public static class Config {
        private boolean enabled = true;
    }

    @Autowired
    public JwtAuthFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 1. 检查过滤器是否启用
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }

            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            // 2. 白名单检查
            if (isWhiteList(path, whiteList)) {
                return chain.filter(exchange);
            }

            // 3. 提取token
            String token = extractToken(request);
            if (!StringUtils.hasText(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            // 2. 解析token
            Claims claims = jwtUtil.parseToken(token);
            if (claims == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // 3. ✅ 关键：将用户信息添加到Header
            ServerHttpRequest newRequest = request.mutate()
                    .header("X-User-Id", String.valueOf(claims.get("userId", Long.class)))
                    .header("X-Username", claims.get("username", String.class))
                    .header("X-User-Role", String.valueOf(claims.get("role", Integer.class)))
                    .header("X-Token", token)  // 可选：传递原始token
                    .build();

            return chain.filter(exchange.mutate().request(newRequest).build());
        };
    }

    private String extractToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // 去掉"Bearer "前缀
        }
        return null;
    }

    private boolean isWhiteList(String path, List<String> whiteList) {
        if (whiteList == null || whiteList.isEmpty()) {
            return false;
        }
        return whiteList.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}
