package com.resume.util.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private static String previousDateTime;

    public synchronized static String getUniqueDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateTime = sdf.format(new Date());
        while (dateTime.equals(previousDateTime)) {
            dateTime = sdf.format(new Date());
        }
        previousDateTime = dateTime;
        return dateTime;
    }

    /**
     * long转换为时间
     *
     * @param millSec
     * @return
     */
    public static String longToDate(Long millSec) {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 将当前系统时间转换成直至Millisecond的样式
     */
    public static String getDateTimeZone() {
        return new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
    }

    /**
     * 将当前系统时间转换成直至HHmmssS的样式
     */
    public static String getTimeZone() {
        return new SimpleDateFormat("HHmmssS").format(new Date());
    }

    /**
     * 根据时间格式取当前时间
     *
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 得到当前系统时间和日期
     */
    public static String getDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getDateTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 获得当前系统日期
     */
    public static String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 获得当前系统时间
     */
    public static String getTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static boolean isDateAfter(String timeString, long rating) {
        try {
            Date date = addDay(timeString, rating);
            Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            long min = date.getTime();
            long nowmin = now.getTime();
            if (nowmin - min > 10 * 60 * 1000)
                return true;
            else
                return false;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 判断传入的日期是否大于当前系统时间额定的时间
     *
     * @param timeString
     * @param rating
     */
    public static boolean isAfter(String timeString, int rating) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeString);
            Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            long min = date.getTime();
            long nowmin = now.getTime();
            long count = rating * 60 * 1000;
            if (nowmin - min > count)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断传入的日期是否大于当前系统时间额定的时间
     *
     * @param timeString
     * @param rating
     */
    public static boolean isBefore(String timeString, int rating) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeString);
            Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            long min = date.getTime();
            long nowmin = now.getTime();
            if (nowmin - min < rating * 60 * 1000)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据指定的日期格式显示日期
     *
     * @param dDate
     * @param sFormat
     */
    public static String formatDate(Date dDate, String sFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
        String dateString = formatter.format(dDate);
        return dateString;
    }

    public static String dateToString(Date dDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dDate);
        return dateString;
    }

    public static Date toDate(Date dDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dDate);
        return strToDate(dateString, "yyyy-MM-dd");
    }

    public static Date toDatetime(Date dDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dDate);
        return strToDate(dateString, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将字符转换为指定的日期格式输出
     *
     * @param s
     * @param pattern
     */
    public static Date strToDate(String s, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date1;
        try {
            Date theDate = formatter.parse(s);
            Date date = theDate;
            return date;
        } catch (Exception ex) {
            date1 = null;
        }
        return date1;
    }

    public static Date strToDateTime(String s) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1;
        try {
            Date theDate = formatter.parse(s);
            Date date = theDate;
            return date;
        } catch (Exception ex) {
            date1 = null;
        }
        return date1;
    }

    /**
     * 将字符转换为日期
     *
     * @param s
     */
    public static Date strToDate(String s) {
        Date date = null;
        try {
            DateFormat df = DateFormat.getDateInstance();
            date = df.parse(s);
        } catch (Exception e) {
            //ignore
        }
        return date;
    }

    /**
     * 添加小时和分
     */
    public static Date addMinute(String sDate, long iNbTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(sDate, "yyyy-MM-dd HH:mm"));
        cal.add(Calendar.MINUTE, (int) iNbTime);
        Date date = cal.getTime();
        return date;
    }

    public static Date addHour(String sDate, long iNbTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(sDate, "yyyy-MM-dd HH:mm"));
        cal.add(Calendar.HOUR_OF_DAY, (int) iNbTime);
        Date date = cal.getTime();
        return date;
    }

    /**
     * 给指定添加天数
     */
    public static Date addDay(String sDate, long iNbDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(sDate, "yyyy-MM-dd HH:mm"));
        cal.add(Calendar.DAY_OF_MONTH, (int) iNbDay);
        Date result = cal.getTime();
        return result;
    }

    /**
     * 给当前时间添加天数
     */
    public static Date addDay(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 返回当前日期减去指定天数的日期
     *
     * @param days
     * @return
     */
    public static Date subDay(int days) {
        return addDay(-days);
    }

    /**
     * 返回指定日期减去指定天数的日期
     *
     * @param days
     * @return
     */
    public static Date subDay(Date specialDate, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(specialDate);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return addDay(-days);
    }

    /**
     * 给当前时间添加 周
     */
    public static Date addWeek(String sDate, long iNbWeek) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(sDate, "yyyy-MM-dd HH:mm"));
        cal.add(Calendar.WEEK_OF_YEAR, (int) iNbWeek);
        Date result = cal.getTime();
        return result;
    }

    /**
     * 给当前时间添加 月
     */
    public static Date addMonth(String sDate, int iNbMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(strToDate(sDate, "yyyy-MM-dd HH:mm"));
        int month = cal.get(Calendar.MONTH);
        month += iNbMonth;
        int year = month / 12;
        month %= 12;
        cal.set(Calendar.MONTH, month);
        if (year != 0) {
            int oldYear = cal.get(Calendar.YEAR);
            cal.set(Calendar.YEAR, year + oldYear);
        }
        return cal.getTime();
    }

    /**
     * 给单当前时间添加 年
     *
     * @param dDate
     * @param iNbYear
     */
    public static Date addYear(Date dDate, int iNbYear) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        int oldYear = cal.get(1);
        cal.set(1, iNbYear + oldYear);
        return cal.getTime();
    }

    /**
     * 得到当前日期是星期几
     *
     * @param dDate
     */
    public static int getWeek(Date dDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 得到当前天是星期几
     *
     * @return
     * @author Cui WenKe
     * @date Aug 30, 2008 9:48:45 PM
     */
    public static String getWeeks() {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // SimpleDateFormat 是一个以与语言环境相关的方式来格式化和分析日期的具体类。它允许进行格式化（日期 -> 文本）、分析（文本
        // -> 日期）和规范化。
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            date = sdfInput.parse(sdfInput.format(date));
        } catch (ParseException ex) {
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);// get 和 set
        // 的字段数字，指示一个星期中的某天。
        System.out.println("dayOfWeek:" + dayOfWeek);
        return dayNames[dayOfWeek - 1];
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 得到但前日期是 那个月的几号
     *
     * @param dDate
     */
    public static int getMonthOfDay(Date dDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当前时间的分钟
     *
     * @param timeStamp
     * @return
     */
    public static int getMinuteOfHour(Date timeStamp) {
        if (timeStamp != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timeStamp);
            return calendar.get(Calendar.MINUTE);
        }
        return 0;
    }

    public static int getHourOfDay(Date timeStamp) {
        if (timeStamp != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timeStamp);
            return calendar.get(Calendar.HOUR_OF_DAY);
        }
        return 0;
    }

    /**
     * 生成随即数
     *
     * @param pwd_len
     * @return
     * @author Cui WenKe
     * @date 2008-4-3 上午09:57:56
     */
    public static String genRandomNum(int pwd_len) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    public static String genRandNum(int pwd_len) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    /**
     * 生成随机字母
     */
    public static String genRandomChar() {
        final int maxNum = 25;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 12) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString().toUpperCase();
    }

    /**
     * 得到当前年
     */
    public static String getNowYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 得到当前季度 1 第一季度  2 第二季度 3 第三季度 4 第四季度
     */
    public static int getSeason() {
        int season = 0;
        int month = Calendar.getInstance().get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static long translateDateToSecond(Date date) {
        if (date != null) {
            return date.getTime() / 1000;
        }
        return 0;
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String encode(String src, String enCoding) {
        String target = null;
        if (src == null)
            return null;
        try {
            target = URLEncoder.encode(src, enCoding);
        } catch (UnsupportedEncodingException e) {
            logger.error(String.valueOf(e));
        }
        return target;
    }

    public static int getYearOfDate(Date date) {
        int year = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            year = calendar.get(Calendar.YEAR);
        } catch (Exception e) {
            //ignore
        }
        return year;
    }

    public static int getMonthOfDate(Date date) {
        int month = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            month = calendar.get(Calendar.MONTH);
        } catch (Exception e) {
            //ignore
        }
        return month;
    }

    public static int getDayOfDate(Date date) {
        int day = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            //ignore
        }
        return day;
    }

    /**
     * 通用时间格式
     */
    public static final String PATTERN_STANDARD = "yyyy-MM-dd HH:mm:ss";
    /**
     * 通用日期格式
     */
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String START_TIME = " 00:00:00";
    public static final String END_TIME = " 23:59:59";
    public static final String START_DATE = "2000-01-01";
    public static final String END_DATE = "2200-01-01";
    /**
     * 参数不能为空
     */
    public static final String PARM_IS_NULL = "parm is not allow null!";

    private DateUtil() {
        throw new UnsupportedOperationException("Not allow construct!");
    }

    /**
     * Timestamp时间类型按指定格式转字符串
     *
     * @param timestamp 为空则抛出异常
     * @param pattern   如果为空则取默认的时间格式输出
     * @throws IllegalArgumentException
     * @功能: 会抛出异常
     */
    public static String timestamp2String(Timestamp timestamp, String pattern) throws IllegalArgumentException {
        if (timestamp == null) {
            throw new IllegalArgumentException(PARM_IS_NULL);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(StringUtil.isNull(pattern) ? PATTERN_STANDARD : pattern);
        return sdf.format(new Date(timestamp.getTime()));
    }

    /**
     * 按默认格式将date时间类型转字符串
     *
     * @param date
     * @功能: 会抛出异常
     */
    public static String date2String(Date date) {
        return date2String(date, null);
    }

    /**
     * 按按指定格式将date时间类型转字符串
     *
     * @param date    为空则抛出异常
     * @param pattern 如果为空则取默认的时间格式输出
     * @throws NullPointerException
     * @功能: 时间格式字符串，会抛出异常
     */
    public static String date2String(Date date, String pattern) throws NullPointerException {
        if (date == null) {
            throw new NullPointerException(PARM_IS_NULL);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(StringUtil.isNull(pattern) ? PATTERN_STANDARD : pattern);
        return sdf.format(date);
    }

    /**
     * 获取当前运行时间
     *
     * @功能: 返回Timestamp类型
     */
    public static Timestamp currentTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    /**
     * 按指定格式获取当前时间
     *
     * @param pattern 指定时间字符串格式，为空值或空对象则取默认格式
     * @功能: 字符串
     */
    public static String currentTimestamp2String(String pattern) {
        return timestamp2String(currentTimestamp(), pattern);
    }

    /**
     * 获取当前时间
     *
     * @功能: 返回默认时间格式的字符串
     */
    public static String currentTimestamp2String() {
        return timestamp2String(currentTimestamp(), null);
    }

    /**
     * 将字符串转成时间
     *
     * @param strDateTime 待转时间格式字符串,为空指或空对象则抛出异常
     * @param pattern     指定字符串的时间格式如："yyyy-MM-dd HH:mm:ss",不指定则取默认值
     * @throws NullPointerException,ParseException
     * @功能: 返回Timestamp时间类型, 会抛出运行时异常
     */
    public static Timestamp string2Timestamp(String strDateTime, String pattern) throws NullPointerException, ParseException {
        if (StringUtil.isNull(strDateTime)) {
            throw new NullPointerException(PARM_IS_NULL);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(StringUtil.isNull(pattern) ? PATTERN_STANDARD : pattern);
        Date date = null;
        date = sdf.parse(strDateTime);
        return new Timestamp(date.getTime());
    }

    /**
     * 将字符串转成日期时间
     *
     * @param strDate 待转日期时间格式字符串,为空指或空对象则抛出异常
     * @param pattern 指定字符串的时间格式如："yyyy-MM-dd",不指定则取默认值
     * @throws NullPointerException,ParseException
     * @功能: 返回Date时间类型, 会抛出运行时异常
     */
    public static Date string2Date(String strDate, String pattern) throws NullPointerException, ParseException {
        if (StringUtil.isNull(strDate)) {
            throw new NullPointerException(PARM_IS_NULL);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(StringUtil.isNull(pattern) ? PATTERN_STANDARD : pattern);
        Date date = null;
        date = sdf.parse(strDate);
        return date;
    }

    /**
     * 从输入的日期时间字符串中提取年份数字
     *
     * @param strDest 输入的字符串日期，为空则抛出异常
     * @throws NullPointerException,ParseException
     * @功能: 返回字符串，会抛出运行时异常
     */
    public static String stringToYear(String strDest) throws NullPointerException, ParseException {
        if (StringUtil.isNull(strDest)) {
            throw new NullPointerException(PARM_IS_NULL);
        }
        Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return String.valueOf(c.get(Calendar.YEAR));
    }

    /**
     * 从输入的日期时间字符串中提取月份数字
     *
     * @param strDest 输入的字符串日期，为空则抛出异常
     * @throws NullPointerException,ParseException
     * @功能: 返回两位字符串，如01、12，会抛出运行时异常
     */
    public static String stringToMonth(String strDest) throws NullPointerException, IllegalArgumentException, ParseException {
        if (StringUtil.isNull(strDest)) {
            throw new NullPointerException(PARM_IS_NULL);
        }
        Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // return String.valueOf(c.get(Calendar.MONTH));
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        if (month < 10) {
            return "0" + month;
        }
        return String.valueOf(month);
    }

    /**
     * 从输入的日期时间字符串中提取日数字
     *
     * @param strDest 输入的字符串日期，为空则抛出异常
     * @throws NullPointerException,ParseException
     * @功能: 返回两位字符串，如01、12，会抛出运行时异常
     */
    public static String stringToDay(String strDest) throws NullPointerException, ParseException {
        if (StringUtil.isNull(strDest)) {
            throw new NullPointerException(PARM_IS_NULL);
        }
        Date date = string2Date(strDest, DateUtil.PATTERN_DATE);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            return "0" + day;
        }
        return Integer.toString(day);
    }

    /**
     * 获取指定月的第一天
     *
     * @param c
     * @功能:
     */
    public static Date getFirstDayOfMonth(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = 1;
        c.set(year, month, day, 0, 0, 0);
        return c.getTime();
    }

    /**
     * 获取指定月的最后一天
     *
     * @param c
     * @功能:
     */
    public static Date getLastDayOfMonth(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = 1;
        if (month > 11) {
            month = 0;
            year = year + 1;
        }
        c.set(year, month, day - 1, 0, 0, 0);
        return c.getTime();
    }

    /**
     * 将普通时间类型转为日历时间类型字符输出
     *
     * @param date
     * @throws DatatypeConfigurationException
     * @功能:
     */
    public static String date2GregorianCalendarString(Date date) throws DatatypeConfigurationException {
        if (date == null) {
            throw new IllegalArgumentException(PARM_IS_NULL);
        }
        long tmp = date.getTime();
        GregorianCalendar ca = new GregorianCalendar();
        ca.setTimeInMillis(tmp);
        XMLGregorianCalendar t_XMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(ca);
        return t_XMLGregorianCalendar.normalize().toString();
    }

    /**
     * 比较时间是否完全一致
     *
     * @param firstDate
     * @param secondDate
     * @功能: 一致则返回true
     */
    public static boolean compareDate(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            throw new RuntimeException();
        }
        String strFirstDate = date2String(firstDate, PATTERN_DATE);
        String strSecondDate = date2String(secondDate, PATTERN_DATE);
        return strFirstDate.equals(strSecondDate);
    }

    /**
     * 根据日期获取当天开始时间(0点)
     *
     * @param currentDate
     * @throws ParseException
     * @throws NullPointerException
     * @功能: Date类型
     */
    public static Date getStartTimeOfDate(Date currentDate) throws NullPointerException, IllegalArgumentException, ParseException {
        String strDateTime = date2String(currentDate, PATTERN_DATE) + " 00:00:00";
        return string2Date(strDateTime, "yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 根据日期获取当天的结束时间
     *
     * @param currentDate
     * @throws ParseException
     * @throws NullPointerException
     * @功能: 时间格式如下:"yyyy-MM-dd 23:59:59"
     */
    public static Date getEndTimeOfDate(Date currentDate) throws NullPointerException, ParseException {
        String strDateTime = date2String(currentDate, PATTERN_DATE) + " 23:59:59";
        return string2Date(strDateTime, "yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 按指定格式转换时间
     *
     * @param date
     * @param pattern 时间格式如:yyyy-MM-dd
     * @throws ParseException
     * @throws NullPointerException
     * @功能: Date 如输入date为空，则抛出异常
     */
    public static Date dateFormat(Date date, String pattern) throws NullPointerException, ParseException {
        String strDateTime = date2String(date);
        return string2Date(strDateTime, pattern);
    }

    /**
     * 获取指定时间的前一天
     *
     * @param date
     * @功能: Date
     */
    public static Date getBeforeDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    /**
     * 获取指定时间的后一天
     *
     * @param date
     * @功能: Date
     */
    public static Date getAfterDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    /**
     * 获得当前时间的前多少天
     *
     * @param date
     * @param i
     * @功能:
     */
    public static Date getBeforeDate(Date date, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -i);
        return c.getTime();
    }

    /**
     * 获得当前时间的前多少月
     *
     * @param date
     * @param i
     * @功能:
     */
    public static Date getBeforeMonth(Date date, int i) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -i);
        return c.getTime();
    }

    /**
     * 将字符串转成时间戳
     *
     * @param strDateTime 待转时间格式字符串,为空指或空对象则抛出异常
     * @param pattern     指定字符串的时间格式如："yyyy-MM-dd HH:mm:ss",不指定则取默认值
     * @throws NullPointerException,ParseException
     * @功能: 返回Long类型, 会抛出运行时异常
     */
    public static Long stringToTimestamp(String strDateTime, String pattern) throws NullPointerException, ParseException {
        if (StringUtil.isNull(strDateTime)) {
            throw new NullPointerException(PARM_IS_NULL);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(StringUtil.isNull(pattern) ? PATTERN_STANDARD : pattern);
        Date date = null;
        date = sdf.parse(strDateTime);
        return date.getTime();
    }

    /**
     * 获取指定年的最后一天
     *
     * @param year
     * @功能:
     */
    public static Date getLastDayOfYear(String year) throws ParseException {
        String endDate = year + "-12-31 23:59:59";
        return string2Date(endDate, PATTERN_STANDARD);
    }

    /**
     * 获取指定年的第一天
     *
     * @param year
     * @功能:
     */
    public static Date getFirstDayOfYear(String year) throws ParseException {
        String endDate = year + "-1-1 00:00:00";
        return string2Date(endDate, PATTERN_STANDARD);
    }

    public static void main(String[] args) {
        //		Date date = new Date();
        //		System.out.println(getBeforeDate(date, 2));
        //		System.out.println(String.valueOf(new Date().getTime()));
        //System.out.println(getEndTimeOfDate(date));

		/*String str1 = "2011-01-01";
        String str2 = "1988-09-09";
		Date date1 = DateUtil.string2Date(str1, "yyyy-MM-dd");
		Date date2 = DateUtil.string2Date(str2, "yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		c2.add(Calendar.YEAR, 4);
		if (c2.before(c1)) {
			System.out.println("illegal");
		}else {
			System.out.println("ok");
		}*/
        //		Date date = new Date();
        //		Calendar calendar = Calendar.getInstance();
        //		calendar.add(Calendar.DATE, -1);
        //		System.out.println(calendar.getTime());
        //		String str1 = "2015";
        //		Date date1 = DateUtil.string2Date(str1, "yyyy-MM-dd");
        //		//getBeforeDate(date1);
        //		System.out.println(getBeforeDate(date1));
        String year = "2014年";
        Date startDate = null;
        try {
            startDate = string2Date(year, "yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(startDate);
    }
}