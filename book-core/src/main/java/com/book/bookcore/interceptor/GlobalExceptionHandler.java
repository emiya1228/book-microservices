package com.book.bookcore.interceptor;

import com.book.bookcommon.exception.ServiceException;
import com.book.bookcommon.result.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理你的业务异常
     * @ResponseStatus 可以设置返回的HTTP状态码，这里用200表示业务逻辑错误
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.OK)  // 返回200，错误信息在body中
    public Response handleServiceException(ServiceException ex, WebRequest request) {
        return new Response(
                ex.getCode(),
                ex.getMessage(),
                null,null
        );
    }



}
