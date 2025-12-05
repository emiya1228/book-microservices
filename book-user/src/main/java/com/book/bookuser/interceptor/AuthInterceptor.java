package com.book.bookuser.interceptor;


import com.book.bookcommon.config.UserContext;
import com.book.bookcommon.exception.ServiceException;
import com.book.bookuser.util.JwtUtil;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 从Header获取Token
        String token = request.getHeader("Authorization").substring(7);
        if (StringUtils.isEmpty(token)) {
            throw new ServiceException("未提供认证Token", 1002);
        }

        UserContext.setCurrentUser(jwtUtil.getUserId(token), jwtUtil.getUsername(token), jwtUtil.getRole(token));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        // 请求结束后清理线程变量，防止内存泄漏
        UserContext.clear();
    }


}
