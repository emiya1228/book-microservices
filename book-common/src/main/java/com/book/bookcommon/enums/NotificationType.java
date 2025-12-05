package com.book.bookcommon.enums;



import lombok.Getter;

/**
 * 通知类型枚举
 */
@Getter
public enum NotificationType {

    BORROW_SUCCESS(0, "借阅成功", "图书借阅成功通知"),
    RETURN_REMINDER(1, "归还提醒", "借阅到期前提醒"),
    OVERDUE_NOTICE(2, "超期通知", "借阅超期通知"),
    RESERVATION_AVAILABLE(3, "预约到书", "预约图书可借通知"),
    FINE_NOTICE(4, "罚款通知", "超期罚款通知"),
    SYSTEM_NOTICE(5, "系统通知", "系统公告"),
    ;

    private final Integer code;
    private final String name;
    private final String description;

    NotificationType(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
}