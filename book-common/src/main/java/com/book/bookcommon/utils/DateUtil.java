package com.book.bookcommon.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期时间工具类
 */
public class DateUtil {

    // 常用格式
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_PATTERN_COMPACT = "yyyyMMddHHmmss";
    public static final String DATE_PATTERN_CHINESE = "yyyy年MM月dd日";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    private DateUtil() {
        // 工具类，防止实例化
    }

    // ==================== 当前时间 ====================

    /**
     * 获取当前日期字符串 (yyyy-MM-dd)
     */
    public static String getCurrentDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     * 获取当前时间字符串 (yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    /**
     * 获取当前时间戳（秒）
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间戳（毫秒）
     */
    public static long getCurrentTimestampMillis() {
        return System.currentTimeMillis();
    }

    // ==================== 格式转换 ====================

    /**
     * LocalDateTime 转字符串
     */
    public static String format(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(DATETIME_FORMATTER);
    }

    /**
     * LocalDateTime 转字符串（自定义格式）
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        return dateTime == null ? null : dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * LocalDate 转字符串
     */
    public static String format(LocalDate date) {
        return date == null ? null : date.format(DATE_FORMATTER);
    }

    /**
     * Date 转字符串
     */
    public static String format(Date date) {
        return date == null ? null : new SimpleDateFormat(DATETIME_PATTERN).format(date);
    }

    /**
     * 字符串转 LocalDateTime
     */
    public static LocalDateTime parseToDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    /**
     * 字符串转 LocalDate
     */
    public static LocalDate parseToDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * 字符串转 Date
     */
    public static Date parseToUtilDate(String dateTimeStr) throws ParseException {
        return new SimpleDateFormat(DATETIME_PATTERN).parse(dateTimeStr);
    }

    // ==================== 时间计算 ====================

    /**
     * 获取几天后的日期
     */
    public static LocalDate plusDays(LocalDate date, int days) {
        return date.plusDays(days);
    }

    /**
     * 获取几天前的日期
     */
    public static LocalDate minusDays(LocalDate date, int days) {
        return date.minusDays(days);
    }

    /**
     * 计算两个日期之间的天数差
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        return end.toEpochDay() - start.toEpochDay();
    }

    /**
     * 计算借阅到期时间（默认30天）
     */
    public static LocalDate calculateDueDate() {
        return LocalDate.now().plusDays(30);
    }

    /**
     * 计算借阅到期时间（自定义天数）
     */
    public static LocalDate calculateDueDate(int borrowDays) {
        return LocalDate.now().plusDays(borrowDays);
    }

    /**
     * 判断是否过期
     */
    public static boolean isOverdue(LocalDate dueDate) {
        return LocalDate.now().isAfter(dueDate);
    }

    /**
     * 计算超期天数
     */
    public static long calculateOverdueDays(LocalDate dueDate) {
        if (!isOverdue(dueDate)) {
            return 0;
        }
        return daysBetween(dueDate, LocalDate.now());
    }

    // ==================== 其他常用方法 ====================

    /**
     * 获取某天的开始时间（00:00:00）
     */
    public static LocalDateTime getDayStart(LocalDate date) {
        return date.atStartOfDay();
    }

    /**
     * 获取某天的结束时间（23:59:59）
     */
    public static LocalDateTime getDayEnd(LocalDate date) {
        return date.atTime(23, 59, 59);
    }

    /**
     * 获取本周一的日期
     */
    public static LocalDate getMondayOfWeek() {
        return LocalDate.now().with(DayOfWeek.MONDAY);
    }

    /**
     * 获取月份的第一天
     */
    public static LocalDate getFirstDayOfMonth() {
        return LocalDate.now().withDayOfMonth(1);
    }

    /**
     * 获取月份的最后一天
     */
    public static LocalDate getLastDayOfMonth() {
        return LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
    }

    /**
     * 判断是否是周末
     */
    public static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}