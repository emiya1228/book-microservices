package com.book.bookcommon.constant;

/**
 * Redis相关常量
 */
public class RedisConstants {

    // ==================== Key 前缀 ====================

    /**
     * 登录用户 key 前缀
     */
    public static final String LOGIN_TOKEN_KEY = "login:token:";

    /**
     * 验证码 key 前缀
     */
    public static final String CAPTCHA_CODE_KEY = "captcha:code:";

    /**
     * 用户信息 key 前缀
     */
    public static final String USER_INFO_KEY = "user:info:";

    /**
     * 图书信息 key 前缀
     */
    public static final String BOOK_INFO_KEY = "book:info:";

    /**
     * 借阅记录 key 前缀
     */
    public static final String BORROW_RECORD_KEY = "borrow:record:";

    /**
     * 热门图书 key
     */
    public static final String HOT_BOOKS_KEY = "books:hot";

    /**
     * 新书上架 key
     */
    public static final String NEW_BOOKS_KEY = "books:new";

    /**
     * 图书分类 key
     */
    public static final String BOOK_CATEGORY_KEY = "books:category";

    // ==================== 过期时间（秒） ====================

    /**
     * 登录token过期时间（24小时）
     */
    public static final Long LOGIN_TOKEN_TTL = 24 * 60 * 60L;

    /**
     * 验证码过期时间（5分钟）
     */
    public static final Long CAPTCHA_CODE_TTL = 5 * 60L;

    /**
     * 用户信息过期时间（2小时）
     */
    public static final Long USER_INFO_TTL = 2 * 60 * 60L;

    /**
     * 图书信息过期时间（6小时）
     */
    public static final Long BOOK_INFO_TTL = 6 * 60 * 60L;

    /**
     * 借阅记录过期时间（30天）
     */
    public static final Long BORROW_RECORD_TTL = 30 * 24 * 60 * 60L;

    /**
     * 热门图书过期时间（6小时）
     */
    public static final Long HOT_BOOKS_TTL = 6 * 60 * 60L;
}