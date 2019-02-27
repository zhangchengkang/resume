package com.resume.util.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class NumberUtil {
    /**
     * 数字格式化对象
     */
    private final static NumberFormat theNumberFormatter = NumberFormat.getNumberInstance();

    private NumberUtil() {
    }

    /**
     * 是否包含数字
     *
     * @param str
     */
    public static boolean isInteger(String str) {
        return isMatch("^-?\\d+$", str);
    }

    /**
     * 是否包含浮点数据
     *
     * @param str
     */
    public static boolean isFloat(String str) {
        return isMatch("^(-?\\d+)(\\.\\d+)?$", str);
    }

    /**
     * 指定的字符串是否为正确的日期时间格式
     *
     * @param str
     */
    public static boolean isDateTime(String str) {
        String patten = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|" +
                "" + "([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|" + "" +
                "" + "(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        return isMatch(patten, str);
    }

    public static boolean isDate(String str) {
        String patten = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|" +
                "" + "([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|" + "" +
                "" + "(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))$";
        return isMatch(patten, str);
    }

    public static boolean isBoolean(String str) {
        String[] values = {"true", "false", "yes", "no", "on", "off", "1", "0"};
        for (String value : values) {
            if (value.equalsIgnoreCase(str)) return true;
        }
        return false;
    }

    private static boolean isMatch(String ms, String str) {
        Pattern p = Pattern.compile(ms);
        return p.matcher(str).matches();
    }

    /**
     * 判断一个字符串是否为long型
     *
     * @param str 被判断的字符串
     * @功能: =true 是long型的字符串 =false 不是long型的字符串
     */
    public static boolean isLong(String str) {
        boolean b = true;
        try {
            Long l = Long.parseLong(str);
            b = l > 0;
        } catch (Exception e) {
            b = false;
            log.println(e);
        }
        return b;
    }

    /**
     * 把浮点型转换成含有指定小数点位数的字符串
     *
     * @param num           浮点型数字
     * @param fractionDigit 小数点位数
     * @功能: 带指定小树位数的浮点字符串
     */
    public static String toString(double num, int fractionDigit) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(1);
        nf.setMinimumFractionDigits(fractionDigit);
        nf.setMaximumFractionDigits(fractionDigit);
        return nf.format(num);
    }

    /**
     * 把一个浮点型数字转换成字符串
     *
     * @param num 要转换的浮点型数值
     * @功能: 带小数点的字符串
     */
    public static String toString(double num) {
        NumberFormat f = NumberFormat.getInstance();
        if (f instanceof DecimalFormat) {
            ((DecimalFormat) f).setDecimalSeparatorAlwaysShown(true);
        }
        f.setParseIntegerOnly(true);
        return f.format(num);
    }

    /**
     * 把一个字符串转换成整数
     *
     * @param str 整型字符串
     * @功能: 整型数字
     */
    public static int toInt(String str) throws Exception {
        return toInt(str, 0);
    }

    /**
     * 把一个字符串转换成整数對象
     *
     * @param str 整型字符串
     * @功能: 整型数字
     */
    public static Integer toIntObject(String str) {
        return new Integer(str);
    }

    /**
     * 把一个字符串转换成整数，如果字符串為空就返回指定整形值
     *
     * @param str          整型字符串
     * @param defaultValue 默認的整型值
     * @功能: 整型数字
     */
    public static int toInt(String str, int defaultValue) {
        if (StringTool.empty(str)) {
            return defaultValue;
        }
        if (!isInteger(str)) {
            return defaultValue;
        }
        return toRawInt(str);
    }

    /**
     * 把一个字符串转换成整数
     *
     * @param str 要转换的字符串
     * @throws Exception 转换错误的返回值
     * @功能: 整数
     */
    public static int toRawInt(String str) {
        return Integer.parseInt(str.trim());
    }

    public static int toFormattedInt(String str) throws Exception {
        return toFormattedInt(str, 0);
    }

    /**
     * 把一个字符串转换成整数，如果字符串为空或转换错误就返回缺省值
     *
     * @param str          被转换的字符串
     * @param defaultValue 缺省值
     * @功能: 转换后的整数
     */
    public static int toFormattedInt(String str, int defaultValue) throws Exception {
        if (StringTool.empty(str)) {
            return defaultValue;
        }
        return toRawFormattedInt(str);
    }

    /**
     * 把一个字符串转换成整数
     *
     * @param str 被转换的字符串
     * @throws Exception 转换错误
     * @功能: 转换后的整数
     */
    public static int toRawFormattedInt(String str) throws Exception {
        return Integer.parseInt(theNumberFormatter.parse(str.trim()).toString());
    }

    /**
     * 把字符串转换为long型，转换失败就返回0L
     *
     * @param str 需要转换的字符串
     * @功能: 转换后的Long型数字
     */
    public static long toLong(String str) throws Exception {
        return toLong(str, 0L);
    }

    /**
     * 把字符串转换为Long型
     *
     * @param str 需要转换的字符串
     * @功能: 转换后的long型数字
     */
    public static Long toLongObject(String str) {
        return new Long(str.trim());
    }

    /**
     * 把字符串转换为Long型，转换失败就返回指定缺省值
     *
     * @param str          需要转换的字符串
     * @param defaultValue 指定的缺省字符串
     * @功能: 转换后的Long型数字
     */
    public static Long toLongObject(String str, long defaultValue) {
        if (StringTool.empty(str)) {
            return new Long(defaultValue);
        }
        return new Long(str.trim());
    }

    /**
     * 把字符串转换为long型，转换失败就返回指定缺省值
     *
     * @param str          需要转换的字符串
     * @param defaultValue 指定的缺省字符串
     * @功能: 转换后的long型数字
     */
    public static long toLong(String str, long defaultValue) {
        if (StringTool.empty(str)) {
            return defaultValue;
        }
        return toRawLong(str);
    }

    /**
     * 把字符串转换成long型
     *
     * @param str 需要转换的字符串
     * @throws Exception 转换失败错误
     * @功能: 转换后的long型数字
     */
    public static long toRawLong(String str) {
        return Long.parseLong(str.trim());
    }

    /**
     * 把字符串转换为long型，转换错误就返回0L
     *
     * @param str 需要转换的字符串
     * @功能: 转换后的字符串
     */
    public static long toFormattedLong(String str) throws Exception {
        return toFormattedLong(str, 0L);
    }

    /**
     * 把字符串转换成long型，转换错误就返回指定的缺省值
     *
     * @param str          需要转换的字符串
     * @param defaultValue 指定的缺省值
     * @功能: 转换后的long型数字
     */
    public static long toFormattedLong(String str, long defaultValue) throws Exception {
        if (StringTool.empty(str)) {
            return defaultValue;
        }
        return toRawFormattedLong(str);
    }

    /**
     * 把字符串转换成long型
     *
     * @param str 转换的字符串
     * @throws Exception 转换失败的错误
     * @功能: 转换后的long型数字
     */
    public static long toRawFormattedLong(String str) throws Exception {
        return Long.parseLong(theNumberFormatter.parse(str.trim()).toString());
    }

    /**
     * 把字符串转换成浮点型数字，转换失败就返回0.0
     *
     * @param str 转换的字符串
     * @功能: 转换后的浮点数字
     */
    public static double toDouble(String str) throws Exception {
        return toDouble(str, 0.0);
    }

    /**
     * 指定给定的double的小数位数
     *
     * @param num           浮点数
     * @param fractionDigit 小数位数
     * @功能: 指定小数位数的浮点数
     */
    public static double toDouble(double num, int fractionDigit) throws Exception {
        String d = toString(num, fractionDigit);
        return toDouble(d, 0.00);
    }

    /**
     * 把字符串转换为浮点型，转换失败就返回指定缺省值
     *
     * @param str          需转换的字符串
     * @param defaultValue 指定的缺省值
     * @功能: 转换好的浮点数字
     */
    public static double toDouble(String str, double defaultValue) throws Exception {
        if (StringTool.empty(str)) {
            return defaultValue;
        }
        return toRawDouble(str);
    }

    /**
     * 把字符串转换成浮点型数字
     *
     * @param str 转换的字符串
     * @throws Exception 转换失败错误
     * @功能: 转换后的浮点数字
     */
    public static double toRawDouble(String str) throws Exception {
        return Double.parseDouble(str.trim());
    }

    /**
     * 把字符串转换成浮点型数字，转换失败就返回0.0
     *
     * @param str 转换的字符串
     * @功能: 转换后的浮点数字
     */
    public static double toFormattedDouble(String str) throws Exception {
        return toFormattedDouble(str, 0.0);
    }

    /**
     * 把字符串转换为浮点型数字，转换失败就返回缺省值
     *
     * @param str          需要转换的字符串
     * @param defaultValue 转换的缺省值
     * @功能: 转换后的浮点型数字
     */
    public static double toFormattedDouble(String str, double defaultValue) throws Exception {
        if (StringTool.empty(str)) {
            return defaultValue;
        }
        return toRawFormattedDouble(str);
    }

    /**
     * 把字符串转换为浮点型数字
     *
     * @param str 需要转换的字符串
     * @throws Exception 转换错误
     * @功能: 浮点型数字
     */
    public static double toRawFormattedDouble(String str) throws Exception {
        return Double.parseDouble(theNumberFormatter.parse(str.trim()).toString());
    }

    /**
     * 将set数组合并成以delim为分割符的字符串
     *
     * @param set   整型数组
     * @param delim 分割符
     * @功能: 带delim分隔符的字符串
     */
    public static String join(int[] set, String delim) {
        if ((null == set) || (set.length <= 0)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(set[0]);
        for (int i = 1; i < set.length; i++) {
            sb.append(delim);
            sb.append(set[i]);
        }
        return sb.toString();
    }

    /**
     * 将数组转换为字符串。
     *
     * @param array 长整型数组
     * @功能: 字符串
     */
    public final static String toString(long[] array) {
        if (null == array) {
            return "count=0:[]";
        }
        String s = "count=" + array.length + ":[ ";
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                s += ", ";
            }
            s += array[i];
        }
        s += " ]";
        return s;
    }

    /**
     * 将整型数组转换成字符串
     *
     * @param array 整型数组
     * @功能: 字符串
     */
    public final static String toString(int[] array) {
        if (null == array) {
            return "count=0:[]";
        }
        String s = "count=" + array.length + ":[ ";
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                s += ", ";
            }
            s += array[i];
        }
        s += " ]";
        return s;
    }

    /**
     * 把阿拉伯数字转换成大写数字.<br>
     * 工程名:<br>
     * 包名:com.kingtopware.common.util<br>
     * 方法名:parseNumber方法.<br>
     *
     * @param num：小写阿拉伯数字
     * @功能: 转换后的数字
     * @author:Cui WenKe
     * @since :1.0:2009-7-18
     */
    public final static String parseNumber(int num) {
        String[] digit = new String[]{"0", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] unit = new String[]{"#", "十", "百", "千", "万", "十", "百", "千", "亿"};
        StringBuilder sb = new StringBuilder();
        int t, unitIndex = 0;
        while (num > 0) {
            t = num % 10;
            if (t > 0 || unitIndex % 4 == 0) sb.append(unit[unitIndex]);
            if (!(unitIndex == 0 && t == 0)) sb.append(digit[t]);
            unitIndex += 1;
            num = num / 10;
        }
        return sb.reverse().toString().replaceAll("0{2,}", "0").replaceAll("0万", "万").replaceFirst("0#", "").replaceFirst("#", "");
    }

    public static void main(String[] args) {
        System.out.println(toString(0.121212211D));
    }
}
