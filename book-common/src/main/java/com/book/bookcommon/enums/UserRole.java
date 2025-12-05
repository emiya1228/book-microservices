package com.book.bookcommon.enums;
import lombok.Getter;

/**
 * 用户角色枚举
 */
@Getter
public enum UserRole {

    READER(0, "读者", "普通读者用户"),
    LIBRARIAN(1, "图书管理员", "管理图书和借阅"),
    ADMIN(2, "系统管理员", "系统管理权限"),
    SUPER_ADMIN(9, "超级管理员", "最高权限");

    private final Integer code;
    private final String name;
    private final String description;

    UserRole(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * 根据code获取枚举
     */
    public static UserRole getByCode(Integer code) {
        for (UserRole role : values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        return READER; // 默认返回读者
    }

    /**
     * 判断是否有管理员权限
     */
    public boolean isAdmin() {
        return this == LIBRARIAN || this == ADMIN || this == SUPER_ADMIN;
    }
}