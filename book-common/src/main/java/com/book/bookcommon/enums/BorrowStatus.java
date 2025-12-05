package com.book.bookcommon.enums;


import lombok.Getter;

/**
 * 借阅状态枚举
 */
@Getter
public enum BorrowStatus {

    BORROWING(0, "借阅中", "图书借出未归还"),
    RETURNED(1, "已归还", "图书已按时归还"),
    OVERDUE(2, "已超期", "图书借阅超期未还"),
    RENEWED(3, "已续借", "图书已办理续借"),
    LOST(4, "已丢失", "借阅期间图书丢失"),
    ;

    private final Integer code;
    private final String name;
    private final String description;

    BorrowStatus(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public static BorrowStatus getByCode(Integer code) {
        for (BorrowStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return BORROWING;
    }

    /**
     * 是否已完成（不需要再处理）
     */
    public boolean isCompleted() {
        return this == RETURNED || this == LOST;
    }
}