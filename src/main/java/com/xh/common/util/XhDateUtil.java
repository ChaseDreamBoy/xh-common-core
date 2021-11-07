package com.xh.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * XhDateUtil
 *
 * @author xh
 * @date 2021/4/10
 */
public class XhDateUtil {

    private XhDateUtil() {
    }

    // =====================================

    public static final String FORMAT_MONTH_INT = "yyyyMM";

    public static final String FORMAT_DAY_INT = "yyyyMMdd";

    public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

    // =====================================

    public static LocalDate addDateYears(LocalDate localDate, long yearsToAdd) {
        return localDate.plusYears(yearsToAdd);
    }

    public static LocalDate addDateMonths(LocalDate localDate, long monthsToAdd) {
        return localDate.plusMonths(monthsToAdd);
    }

    public static LocalDate addDateDay(LocalDate localDate, long daysToAdd) {
        return localDate.plusDays(daysToAdd);
    }

    public static LocalDate addDateWeeks(LocalDate localDate, long weeksToAdd) {
        return localDate.plusWeeks(weeksToAdd);
    }

    public static LocalDate addDatePlugs(LocalDate localDate, long amountToAdd, ChronoUnit unit) {
        return localDate.plus(amountToAdd, unit);
    }

    public static LocalDate subtractDatePlugs(LocalDate localDate, long amountToAdd, ChronoUnit unit) {
        // 减去时间
        return localDate.minus(amountToAdd, unit);
    }

    // =====================================

    public static LocalTime addTimeHours(LocalTime localTime, long hoursToAdd) {
        return localTime.plusHours(hoursToAdd);
    }

    public static LocalTime addTimeMinutes(LocalTime localTime, long minutesToAdd) {
        return localTime.plusMinutes(minutesToAdd);
    }

    public static LocalTime addTimeSeconds(LocalTime localTime, long secondsToAdd) {
        return localTime.plusSeconds(secondsToAdd);
    }

    public static LocalTime addDatePlugs(LocalTime localTime, long amountToAdd, ChronoUnit unit) {
        return localTime.plus(amountToAdd, unit);
    }

    public static LocalTime subtractDatePlugs(LocalTime localTime, long amountToAdd, ChronoUnit unit) {
        // 减去时间
        return localTime.minus(amountToAdd, unit);
    }

    // =====================================

    public static LocalDateTime addDatePlugs(LocalDateTime localDateTime, long amountToAdd, ChronoUnit unit) {
        // 计算 localDateTime 后 amountToAdd 个 unit 的日期
        return localDateTime.plus(amountToAdd, unit);
    }

    public static LocalDateTime subtractDatePlugs(LocalDateTime localDateTime, long amountToAdd, ChronoUnit unit) {
        // 计算 localDateTime 前 amountToAdd 个 unit 的日期
        return localDateTime.minus(amountToAdd, unit);
    }

    // =====================================
    // Clock 时钟类 -- 用于获取当时的时间戳，或当前时区下的日期时间信息。
    // 以前用到 System.currentTimeInMillis() 和 TimeZone.getDefault() 的地方都可用Clock替换。

    public static long getCurTimeMillis() {
        // Clock clock = Clock.systemUTC() -- utc
        return Clock.systemDefaultZone().millis();
    }

    // ===================================== compare

    public static boolean isAfter(LocalDateTime time1, LocalDateTime time2) {
        return time1.isAfter(time2);
    }

    public static boolean isBefore(LocalDateTime time1, LocalDateTime time2) {
        return time1.isBefore(time2);
    }

    // ===================================== 
    // ZoneDateTime -- 处理时区 ZoneId america = ZoneId.of("America/New_York");
    // ZoneId.systemDefault()

    // ===================================== 

    public static int getMonthDayNum(LocalDate localDate) {
        // 获取对应月份的天数
        YearMonth yearMonth = YearMonth.of(localDate.getYear(), localDate.getMonth());
        return yearMonth.lengthOfMonth();
    }

    // ===================================== 

    public static boolean isLeapYear(LocalDate localDate) {
        // 是否是闰年, true -- 是
        return localDate.isLeapYear();
    }

    // =====================================

    public static int getDays(LocalDate localDate1, LocalDate localDate2) {
        // 相差天数
        Period period = Period.between(localDate1, localDate2);
        // period.getMonths() -- 相差月份数
        return period.getDays();
    }

    // =====================================
    // 日期和字符串互转

    public static String dateConvertToStr(LocalDateTime localDateTime, String pattern) {
        // DateTimeFormatter.ISO_DATE_TIME
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime strConvertToDate(String time, String pattern) {
        // DateTimeFormatter.ISO_DATE_TIME
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern));
    }

    // =====================================
    // 日期和时间蹉互转

    public static Long dateConvertToMillis(LocalDateTime localDateTime) {
        // DateTimeFormatter.ISO_DATE_TIME
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime millisConvertToDate(long millis) {
        // DateTimeFormatter.ISO_DATE_TIME
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }

    // =====================================
    // 字符串和时间蹉互转

    public static long strConvertToLong(String time, String pattern) {
        // DateTimeFormatter.ISO_DATE_TIME
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static String millisConvertToStr(long millis, String pattern) {
        // DateTimeFormatter.ISO_DATE_TIME
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern(pattern));
    }

}
