package com.book.bookcommon.enums;

import lombok.Getter;

/**
 * 通用结果码枚举
 */
@Getter
public enum ResultCode {

    // ==================== 成功 ====================
    SUCCESS(200, "操作成功"),

    // ==================== 客户端错误 ====================
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权，请先登录"),
    FORBIDDEN(403, "权限不足，禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),

    // ==================== 业务错误 ====================
    USER_NOT_FOUND(10001, "用户不存在"),
    USERNAME_EXISTS(10002, "用户名已存在"),
    LOGIN_FAILED(10003, "用户名或密码错误"),
    USER_DISABLED(10004, "用户已被禁用"),

    BOOK_NOT_FOUND(20001, "图书不存在"),
    BOOK_NOT_AVAILABLE(20002, "图书不可借"),
    BOOK_ALREADY_BORROWED(20003, "图书已被借出"),

    BORROW_LIMIT_EXCEEDED(30001, "借阅数量已达上限"),
    BORROW_RECORD_NOT_FOUND(30002, "借阅记录不存在"),
    BORROW_ALREADY_RETURNED(30003, "图书已归还"),
    BORROW_OVERDUE(30004, "借阅已超期"),
    RENEW_LIMIT_EXCEEDED(30005, "续借次数已达上限"),

    RESERVATION_EXISTS(40001, "已预约该图书"),
    RESERVATION_NOT_FOUND(40002, "预约记录不存在"),

    FINE_UNPAID(50001, "有未支付的罚款"),
    PAYMENT_FAILED(50002, "支付失败"),

    // ==================== 服务器错误 ====================
    INTERNAL_SERVER_ERROR(50000, "系统内部错误"),
    SERVICE_UNAVAILABLE(50003, "服务暂不可用"),
    DATABASE_ERROR(50004, "数据库操作异常"),
    REDIS_ERROR(50005, "缓存服务异常"),

    // ==================== 通用业务 ====================
    PARAM_VALIDATE_FAILED(60001, "参数校验失败"),
    DATA_NOT_FOUND(60002, "数据不存在"),
    DATA_ALREADY_EXISTS(60003, "数据已存在"),
    OPERATION_FAILED(60004, "操作失败"),
    ;

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}