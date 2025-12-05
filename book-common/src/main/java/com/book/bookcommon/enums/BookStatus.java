package com.book.bookcommon.enums;


import lombok.Getter;

/**
 * 图书状态枚举
 */
@Getter
public enum BookStatus {

    AVAILABLE(0, "可借阅", "图书在馆，可正常借阅"),
    BORROWED(1, "已借出", "图书已被借出"),
    RESERVED(2, "已预约", "图书已被预约"),
    UNDER_REPAIR(3, "维修中", "图书正在维修"),
    LOST(4, "已丢失", "图书已丢失"),
    DISCARDED(5, "已报废", "图书已报废"),
    ;

    private final Integer code;
    private final String name;
    private final String description;

    BookStatus(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public static BookStatus getByCode(Integer code) {
        for (BookStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return AVAILABLE;
    }

    /**
     * 是否可以借阅
     */
    public boolean canBorrow() {
        return this == AVAILABLE;
    }
}