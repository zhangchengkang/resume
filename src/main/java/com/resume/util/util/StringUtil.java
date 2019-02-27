/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 * <p>
 *  @接口方法说明: StringUtil.java
 *  @Prject: kingtopware-common
 *  @Package: com.kingtopware.common.util
 *  @注意事项:常用字符串处理类
 *  @author: chengjin  
 *  @date: 2016年12月1日 下午4:31:12
 *  @version: V1.0  
 *  
 */
package com.resume.util.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名称：Tool
 * 类名称：StringUitl
 * 类描述：
 * 创建人：chengjin
 * 创建时间：2015-6-3 上午09:25:00
 * 修改人：chengjin
 * 修改时间：2015-6-3 上午09:25:00
 * 修改备注：
 */
public class StringUtil {
    /**
     * 默认空白字符串
     */
    public final static String EMPTY_STRING = "";
    /**
     * URL地址链接符
     */
    public final static String URL_CONTACT_SYMOBOL = "?";
    /**
     * 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
     */
    public final static String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>";
    /**
     * 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
     */
    public final static String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>";
    /**
     * 定义HTML标签的正则表达式
     */
    public final static String regEx_html = "<[^>]+>";

    private StringUtil() {
        throw new UnsupportedOperationException("Not allow construct!");
    }

    /**
     * <p>判断字符串是否为空,不包含全部空格的情况</p>
     * <p>Author: 程晋</p>
     *
     * @param str
     * @功能:
     */
    public static boolean isNull(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * <p>转义html字符串中的特殊字符</p>
     * <p>Author: 程晋</p>
     *
     * @param content 替换前的html字符串
     * @功能: 替换后的字符串
     */
    public static String escapeHtml(String content) {
        if (content == null) return "";
        String html = content;
        html = html.replace("&", "&amp;");//&必须第一个替换
//		html = StringTool.replace(html, "'", "&apos;");
//		html = StringTool.replace(html, "\"", "&quot;");
//		html = StringTool.replace(html, "\t", "&nbsp;&nbsp;");// 替换跳格
        // html = StringTool.replace(html, " ", "&nbsp;");// 替换空格
        html = html.replace("<", "&lt;");
        html = html.replace(">", "&gt;");
        return html;
    }

    /**
     * 字符串转换为整型
     *
     * @param str      输入字符串
     * @param defaults 默认值
     *                 若输入字符串为null或不能转为int，则返回设定的默认值
     * @功能: int
     */
    public static int convertInt(String str, int defaults) {
        if (isNull(str)) {
            return defaults;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return defaults;
        }
    }

    /**
     * String转long
     *
     * @param str      输入字符串
     * @param defaults 默认值
     *                 若输入字符串为null或不能转为long，则返回设定的默认值
     * @功能: long参数
     */
    public static long convertLong(String str, long defaults) {
        if (str == null) {
            return defaults;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return defaults;
        }
    }

    /**
     * 字符串转double
     *
     * @param str      输入字符串
     * @param defaults 默认值
     *                 若输入字符串为null或不能转为double，则返回设定的默认值
     * @功能: double型参数
     */
    public static double convertDouble(String str, double defaults) {
        if (str == null) {
            return defaults;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return defaults;
        }
    }

    /**
     * string转为short
     *
     * @param str      输入字符串
     * @param defaults 默认值
     *                 若输入字符串为null或不能转为short，则返回设定的默认值
     * @功能: short型参数
     */
    public static short convertShort(String str, short defaults) {
        if (str == null) {
            return defaults;
        }
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException e) {
            return defaults;
        }
    }

    /**
     * 字符串转float
     *
     * @param str      输入字符串
     * @param defaults 默认值
     *                 若输入字符串为null或不能转为float，则返回设定的默认值
     * @功能: float型参数
     */
    public static float convertFloat(String str, float defaults) {
        if (str == null) {
            return defaults;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return defaults;
        }
    }

    /**
     * string转boolean
     *
     * @param str      输入字符串
     * @param defaults 默认值
     *                 若输入字符串为null或不能转为boolean，则返回设定的默认值
     * @功能: boolean型参数
     */
    public static boolean convertBoolean(String str, boolean defaults) {
        if (str == null) {
            return defaults;
        }
        try {
            return Boolean.parseBoolean(str);
        } catch (NumberFormatException e) {
            return defaults;
        }
    }

    /**
     * 输入非空文本值，过滤HTML标签
     *
     * @param inputString
     * @功能:
     */
    public static String Html2Text(String inputString) {
        if (isNull(inputString)) {
            return inputString;
        }
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
        String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(EMPTY_STRING); // 过滤script标签
        p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(EMPTY_STRING); // 过滤style标签
        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(EMPTY_STRING); // 过滤html标签
        textStr = htmlStr;
        return textStr;// 返回文本字符串
    }

    /**
     * 如果字符串为空，返回指定字符串
     * Author: chengjin
     *
     * @param str        源字符串
     * @param replaceStr 目标字符串
     * @功能: str为空时返回replaseStr，否则返回原str。
     */
    public static String isNullReturnDefaultValue(String str, String replaceStr) {
        return isNull(str) ? replaceStr : str;
    }

    /**
     * 转义Solr/Lucene的保留运算字符
     * 保留字符有+ - && || ! ( ) { } [ ] ^ ” ~ * ? : \
     *
     * @param input
     * @功能: 转义后的字符串
     */
    public static String luceneFilter(String input) {
        if (input == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        String regex = "[+\\-&|!(){}\\[\\]^\"~*?:(\\)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matcher.appendReplacement(sb, "\\\\" + matcher.group());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 将字符串截取，字符串超长部分用...限制展示
     *
     * @param str      字符串
     * @param cutCount 总长度
     * @功能:
     */
    public static String getSubStr(String str, int cutCount) {
        if (str == null) return "";
        StringBuilder resultStr = new StringBuilder();
        char[] ch = str.toCharArray();
        int count = ch.length;
        int strBLen = str.getBytes().length;
        int temp = 0;
        for (int i = 0; i < count; i++) {
            resultStr.append(ch[i]);
            temp = resultStr.toString().getBytes().length;
            if (temp >= cutCount && temp < strBLen) {
                resultStr.append("...");
                break;
            }
        }
        return resultStr.toString();
    }

    /**
     * 判断字符串在数组中是否存在，比较时忽略其大小写
     *
     * @param array
     * @param str
     * @功能:
     */
    public static boolean containsIgnoreCase(String[] array, String str) {
        for (String item : array) {
            if (item.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 随机获取UUID字符串(无中划线)
     *
     * @功能: UUID字符串
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
    }

    /**
     * 随机获取字符串
     *
     * @param length 随机字符串长度
     * @功能: 随机字符串
     */
    public static String getRandomString(int length) {
        if (length <= 0) {
            return "";
        }
        char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(randomChar[Math.abs(random.nextInt(randomChar.length)) % randomChar.length]);
        }
        return stringBuffer.toString();
    }

    /**
     * 根据指定长度 分隔字符串
     *
     * @param str    需要处理的字符串
     * @param length 分隔长度
     * @功能: 字符串集合
     */
    public static List<String> splitString(String str, int length) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i += length) {
            int endIndex = i + length;
            if (endIndex <= str.length()) {
                list.add(str.substring(i, i + length));
            } else {
                list.add(str.substring(i, str.length() - 1));
            }
        }
        return list;
    }

    /**
     * 将字符串List转化为字符串，以分隔符间隔.
     *
     * @param list      需要处理的List.
     * @param separator 分隔符.
     * @功能: 转化后的字符串
     */
    public static String toString(List<String> list, String separator) {
        StringBuilder stringBuffer = new StringBuilder();
        for (String str : list) {
            stringBuffer.append(separator + str);
        }
        stringBuffer.deleteCharAt(0);
        return stringBuffer.toString();
    }

    /**
     * @接口方法说明: isInteger
     * @注意事项: 验证该字符串是否能转换成数字
     * @param: str 字符串
     * @功能: boolean ture:可以转换  false:不能转换
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
