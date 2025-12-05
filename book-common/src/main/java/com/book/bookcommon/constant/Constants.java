package com.book.bookcommon.constant;

/**
 * 系统常量类
 */
public class Constants {

    // ==================== 系统常量 ====================

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 登录成功状态
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 登录失败状态
     */
    public static final String LOGIN_FAIL = "Error";

    // ==================== 业务常量 ====================

    /**
     * 默认借阅天数
     */
    public static final int DEFAULT_BORROW_DAYS = 30;

    /**
     * 最大续借次数
     */
    public static final int MAX_RENEW_COUNT = 1;

    /**
     * 读者最大借阅数量
     */
    public static final int READER_MAX_BORROW_COUNT = 5;
    /**
     * 用户状态_正常
     */
    public static final int USER_STATUS_NORMAL = 1;
    /**
     * 用户状态_禁用
     */
    public static final int USER_STATUS_UNNORMAL = 0;

    /**
     * 超期罚款率（元/天）
     */
    public static final double OVERDUE_FINE_RATE = 0.1;

    /**
     * 预约保留天数
     */
    public static final int RESERVATION_HOLD_DAYS = 3;

    // ==================== 缓存相关常量 ====================

    /**
     * 验证码过期时间（分钟）
     */
    public static final long CAPTCHA_EXPIRE = 5;

    /**
     * 登录token过期时间（小时）
     */
    public static final long TOKEN_EXPIRE = 24;

    /**
     * 热门图书缓存时间（小时）
     */
    public static final long HOT_BOOKS_EXPIRE = 6;

    /**
     * 用户信息缓存时间（小时）
     */
    public static final long USER_INFO_EXPIRE = 2;

    // ==================== 文件相关常量 ====================

    /**
     * 图书封面图片最大大小（5MB）
     */
    public static final long MAX_BOOK_COVER_SIZE = 5 * 1024 * 1024;

    /**
     * 允许的图片类型
     */
    public static final String[] IMAGE_TYPES = { "jpg", "jpeg", "png", "gif" };

    /**
     * 图书封面存储路径
     */
    public static final String BOOK_COVER_PATH = "/upload/book-covers/";

    // ==================== 分页常量 ====================

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc"
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大分页大小
     */
    public static final int MAX_PAGE_SIZE = 100;
}