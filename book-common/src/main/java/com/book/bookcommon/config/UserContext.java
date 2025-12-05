package com.book.bookcommon.config;


import lombok.Data;

/**
 * 用户上下文，用于在整个请求链路中共享用户信息
 */
public class UserContext {

    private static final ThreadLocal<UserInfo> USER_HOLDER = new ThreadLocal<>();

    @Data
    public static class UserInfo {
        private Long userId;
        private String username;
        private String realName;
        private Integer role;
        private String token;
        // 🔥 关键：不包含User实体，只有基本字段
    }

    public static void setCurrentUser(Long userId, String username, Integer role) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUsername(username);
        userInfo.setRole(role);
        USER_HOLDER.set(userInfo);
    }

    public static Long getCurrentUserId() {
        UserInfo userInfo = USER_HOLDER.get();
        return userInfo != null ? userInfo.getUserId() : null;
    }

    public static void clear() {
        USER_HOLDER.remove();
    }
}