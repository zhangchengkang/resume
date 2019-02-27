/**
 * Copyright © 2017湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: StringTool
 * @Prject: server-project
 * @Package: com.kingtopware.common.util
 * @ClassName: StringTool
 * @注意事项: 字符串处理工具类
 * @author: Cui WenKe
 * @date: 2017/4/28
 * @version: V1.0
 */
package com.resume.util.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: StringTool
 * @注意事项: 字符串处理工具类
 * @author: Cui WenKe
 * @date: 2017/4/28
 */
public class StringTool {
    private static final Log log = LogFactory.getLog(StringTool.class);

    public static String GBKToUTF(String str) {
        String utfStr = null;
        try {
            utfStr = new String(str.getBytes("GBK"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utfStr;
    }

    public static String UTFToGBK(String str) {
        String utfStr = null;
        try {
            utfStr = new String(str.getBytes("UTF-8"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utfStr;
    }

    public static String ISOToGBK(String str) {
        String utfStr = null;
        try {
            utfStr = new String(str.getBytes("ISO8859_1"), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utfStr;
    }

    /**
     * 将字符转换成map
     * {row:20000,pageno:1,startdate:2008-9-10,enddate:2008-10-1}
     *     * @return
     * @author Cui WenKe
     * @date Nov 3, 2008 10:03:26 AM
     */
    public static Map<String, String> strToMap(String str) {
        Map<String, String> map = new HashMap<String, String>();
        if (!isBlank(str)) {
            String strObj = str.substring(1, str.length() - 1);
            String[] obj = strObj.split(",");
            for (String value : obj) {
                String[] key = value.split(":");
                map.put(key[0], key[1]);
            }
        }
        return map;
    }

    /**
     * 将map转换成字符
     *
     * @param map
     * @return
     * @author Cui WenKe
     * @date Nov 3, 2008 10:21:31 AM
     */
    public static String mapToStr(Map<String, String> map) {
        String str = "";
        if (map != null) {
            for (Map.Entry<String, String> obj : map.entrySet()) {
                String key = obj.getKey();
                String value = obj.getValue();
                str += key + ":" + value + ",";
            }
        }
        if (str.length() > 0) {
            str = "{" + str.substring(0, str.length() - 1) + "}";
        }
        return str;
    }

    /**
     * @param s
     * @return 如果<tt>s</tt>为<tt>null</tt>或空白字符串返回<tt>true</tt>
     */
    public static boolean isBlank(String s) {
        return s == null ? true : s.trim().length() == 0;
    }

    /**
     * 用<tt>seperator</tt>连接字符串,例如将数组{"a","b","c"}使用';'连接,得到"a;b;c",忽略<tt>null<tt>和空白字符串
     *
     * @param s         需要连接的字符串数组
     * @param seperator 分隔符
     * @return 连接好的字符串或""
     * @throws NullPointerException 如果<tt>s</tt>或<tt>seperator</tt>为<tt>null</tt>
     * @see #join(String[], String, boolean, boolean)
     */
    public static String join(String[] s, String seperator) {
        return join(s, seperator, true, true);
    }

    /**
     * 用<tt>seperator</tt>连接字符串,例如将数组{"a","b","c"}使用';'连接，得到"a;b;c"
     *
     * @param s           需要连接的字符串数组
     * @param seperator   分隔符
     * @param ignoreBlank 是否忽略空字符串,通过<tt>String.trim().length() == 0</tt>判断空字符串
     * @param ignoreNull  是否忽略<tt>null</tt>
     * @return 连接好的字符串或""
     * @throws NullPointerException 如果<tt>s</tt>或<tt>seperator</tt>为<tt>null</tt>
     */
    public static String join(String[] s, String seperator, boolean ignoreBlank, boolean ignoreNull) {
        if (s == null || seperator == null) throw new NullPointerException();
        StringBuilder result = new StringBuilder(256);
        for (String s_ : s) {
            if (ignoreNull && s_ == null) continue;
            else if (ignoreBlank && s_.trim().length() == 0) continue;
            result.append(s_);
            result.append(seperator);
        }
        int i = result.length();
        if (i > 0) return result.substring(0, i - seperator.length());
        else return "";
    }

    /**
     * 将CamelCase转换成大写字母，以“_”为间隔，例如abcFoo转换成ABC_FOO
     *
     * @param s
     * @return
     */
    public static String camelToCapital(String s) {
        final String pattern = "[A-Z]*[a-z0-9]+|[A-Z0-9]+";
        Pattern p = Pattern.compile(pattern); // the expression
        Matcher m = p.matcher(s); // the source
        String r = null;
        while (m.find()) {
            if (r != null) {
                r = r + "_" + m.group().toUpperCase();
            } else r = m.group().toUpperCase();
        }
        return r;
    }

    /**
     * 将大写字母转换成CamelCase，以“_”为间隔，例如ABC_FOO转换成abcFoo
     *
     * @param s
     * @return
     */
    public static String capitalToCamel(String s) {
        String[] tokens = s.split("_");
        String r = tokens[0].toLowerCase();
        for (int i = 1; i < tokens.length; i++) {
            r += (tokens[i].substring(0, 1) + tokens[i].substring(1).toLowerCase());
        }
        return r;
    }

    /**
     * 截取字符串，按照系统默认的字符集，截取后的后缀为“...”
     *
     * @param target   被截取的原字符串，此方法执行前会先<tt>trim</tt>
     * @param maxBytes 截取后字符串的最大<tt>byte</tt>数，包括截取后的字符串的后缀
     * @return
     * @see #substring(String, String, int, String)
     */
    public static String substring(String target, int maxBytes) {
        return substring(target.trim(), Charset.defaultCharset().name(), maxBytes, "...");
    }

    /**
     * 截取字符串
     *
     * @param target   被截取的原字符串
     * @param charset  字符串的字符集
     * @param maxBytes 截取后字符串的最大<tt>byte</tt>数，包括截取后的字符串的后缀
     * @param append   字符串被截去后的后缀
     * @return
     */
    public static String substring(String target, String charset, int maxBytes, String append) {
        try {
            int count = getBytes(target, charset).length;
            if (count <= maxBytes) {
                return target;
            } else {
                int bytesCount = 0;
                char[] replace = new char[getBytes(append, charset).length];
                int j = 0;
                int bound = maxBytes - getBytes(append, charset).length;
                for (int i = 0; i < target.length(); i++) {
                    char c = target.charAt(i);
                    bytesCount = c > 255 ? bytesCount + 2 : bytesCount + 1;
                    if (bytesCount > maxBytes) {
                        return target.substring(0, i - j).concat(append);
                    }
                    if (bytesCount > bound) {
                        replace[j++] = c;
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Unreachable!");
    }

    private static byte[] getBytes(String s, String charset) throws UnsupportedEncodingException {
        return s.getBytes(charset);
    }

    public static boolean isNotBlank(String... strs) {
        boolean isNotNull = true;
        if (strs == null || strs.length == 0) {
            return false;
        }
        for (String str : strs) {
            if (isBlank(str)) {
                isNotNull = false;
                break;
            }
        }
        return isNotNull;
    }

    public static boolean equals(String str, String tar) {
        return isNotBlank(str, tar) && str.toLowerCase().equals(tar.toLowerCase());
    }

    public static String substringAfterLast(String str, String separator) {
        if (!isNotBlank(str, separator)) {
            return "";
        } else {
            int pos = str.lastIndexOf(separator);
            return pos != -1 && pos != str.length() - separator.length() ? str.substring(pos + separator.length()) : "";
        }
    }

    private static char[] constant = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    // 简体中文的编码范围从B0A1（45217）一直到F7FE（63486）
    private static int BEGIN = 45217;
    private static int END = 63486;
    // 按照声 母表示，这个表是在GB2312中的出现的第一个汉字，也就是说“啊”是代表首字母a的第一个汉字。
    // i, u, v都不做声母, 自定规则跟随前面的字母
    private static char[] chartable = {'啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝',};
    // 二十六个字母区间对应二十七个端点
    // GB2312码汉字区间十进制表示
    private static int[] table = new int[27];
    // 对应首字母区间表
    private static char[] initialtable = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 't', 't', 'w', 'x', 'y', 'z',};

    // 初始化
    static {
        for (int i = 0; i < 26; i++) {
            try {
                table[i] = gbValue(chartable[i]);// 得到GB2312码的首字母区间端点表，十进制。
            } catch (UnsupportedEncodingException e) {
            }
        }
        table[26] = END;// 区间表结尾
    }

    private StringTool() {
    }

    /**
     * 判别字符串是否为null或者没有内容
     *
     * @param inStr 被判断的输入参数
     * @功能: 布尔值：true=表示输入字符串为null或者没有内容 false=表示输入字符串不为null或者有内容
     */
    public static boolean zero(String inStr) {
        return ((null == inStr) || (inStr.length() <= 0));
    }

    /**
     * 判断字符串是否为null或没有内容或全部为空格。
     *
     * @param inStr 被判断的输入参数
     * @功能: 布尔值：true=表示输入字符串为null或没有内容或全部为空格 false=表示输入字符串不为null或有内容或全部不为空格
     */
    public static boolean empty(String inStr) {
        return (zero(inStr) || (inStr.trim().equals("")) || (inStr.trim().equals("null")));
    }

    public static JSONObject getNode(String path, Node parent) {
        return getNode(path, getJsonByNode(new JSONObject(), parent));
    }

    public static JSONObject getNode(String path, JSONObject parent) {
        String[] split = path.split("/");
        JSONObject temp = parent;
        for (String iterable : split) {
            if (null != temp.get(iterable)) temp = temp.getJSONObject(iterable);
        }
        return temp;
    }

    public static JSONObject getJsonByNode(JSONObject p, Node parent) {
        JSONObject json = new JSONObject();
        for (int i = 0; i < parent.getChildNodes().getLength(); i++) {
            Node element = parent.getChildNodes().item(i);
            if (element.hasChildNodes()) {
                getJsonByNode(json, element);
            } else {
                json.put(element.getNodeName(), element.getNodeValue());
            }
        }
        return getJsonByNode(json, p, parent);
    }

    public static JSONObject getJsonByNode(JSONObject json, JSONObject p, Node parent) {
        if (p.containsKey(parent.getNodeName())) {
            JSONArray arr = new JSONArray();
            arr.add(p.get(parent.getNodeName()));
            arr.add(json);
            p.remove(parent.getNodeName());
            p.put(parent.getNodeName() + "s", arr);
        } else {
            if (p.containsKey(parent.getNodeName() + "s")) {
                JSONArray arr = (JSONArray) p.get(parent.getNodeName() + "s");
                arr.add(json);
            } else {
                p.put(parent.getNodeName(), json);
            }
        }
        return p;
    }

    public static boolean noEmpty(String inStr) {
        return !empty(inStr);
    }

    public static String getAddress(String inStr) {
        if (zero(inStr)) {
            return "";
        }
        return inStr.indexOf(':') > 0 ? inStr.substring(inStr.lastIndexOf(':')) : inStr;
    }

    public static String getReferer(String referer) {
        if (StringTool.empty(referer)) return referer;
        referer = referer.substring(referer.indexOf("//") + 2);
        if (referer.indexOf(":") > 0) {
            referer = referer.substring(0, referer.indexOf(":"));
        }
        return referer;
    }

    /**
     * 在str为null或者没有内容的情况下，返回空串；否则返回输入参数。
     *
     * @param inStr 被判断的输入参数
     * @功能: 字符串="" 表示输入字符串为null或者没有内容 字符串!="" 表示输入字符串有内容
     */
    public static String toZeroSafe(String inStr) {
        if (zero(inStr)) {
            return "";
        }
        return inStr;
    }

    /**
     * 在inStr为null或者没有内容的情况下，返回def；否则返回str
     *
     * @param inStr 被判断的输入参数
     * @param def   inStr为null或者没有内容的情况下，需要返回的字符串
     * @功能: 字符串=def 表示输入字符串为null或者没有内容 字符串=inStr 表示输入字符串有内容
     */
    public static String toZeroSafe(String inStr, String def) {
        if (zero(inStr)) {
            return def;
        }
        return inStr;
    }

    /**
     * 在str为null或者没有内容，或者全部为空格的情况下，返回空串；否则返回str
     *
     * @param inStr 被判断的输入参数
     * @功能: 字符串="" 表示输入字符串为null或者没有内容或者全部为空格 字符串!="" 表示输入字符串有内容
     */
    public static String toEmptySafe(String inStr) {
        if (empty(inStr)) {
            return "";
        }
        return inStr.trim();
    }

    public static String trimSymbol(String inStr) {
        String inVar = "";
        if (empty(inStr)) {
            return inVar;
        }
        if (inStr.indexOf('\n') != -1) {
            inVar = inStr.replaceAll("\r", "");
        }
        if (inStr.indexOf('\n') != -1) {
            inVar = inStr.replaceAll("\n", "");
        }
        if (inStr.indexOf("\t") != -1) {
            inVar = inStr.replaceAll("\t", "");
        }
        if (inStr.indexOf("\"") != -1) {
            inVar = inStr.replaceAll("\"", "");
        }
        if (inStr.indexOf('\\') != -1) {
            inVar = inStr.replaceAll("\\\\", "");
        }
        if (inStr.indexOf('\b') != -1) {
            inVar = inStr.replaceAll("\b", "");
        }
        return inVar.replaceAll(" ", "");
    }

    /**
     * 在str为null或者没有内容，或者全部为空格的情况下，返回def；否则返回str
     *
     * @param inStr 被判断的输入参数
     * @param def   inStr为null或者没有内容或者全部为空格的情况下，需要返回的字符串
     * @功能: 字符串=def 表示输入字符串为null或者没有内容或者全部为空格 字符串=inStr 表示输入字符串有内容
     */
    public static String toEmptySafe(String inStr, String def) {
        if (empty(inStr)) {
            return def;
        }
        return inStr;
    }

    /**
     * 去掉输入字符串首尾空格
     *
     * @param inStr 输入字符串
     * @功能: 首尾无空格的字符串
     */
    public static String trim(String inStr) {
        if (empty(inStr)) {
            return inStr;
        }
        return inStr.trim();
    }

    public static String trim(String str, String regex) {
        String[] split = str.split(regex);
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (!empty(split[i])) buff.append(regex).append(split[i]);
        }
        return buff.substring(1).toString().trim();
    }

    /**
     * 判断字符串是否内容相同
     *
     * @param s1 第1个输入字符串
     * @param s2 第2个输入字符串
     * @功能: 布尔值=true：两个字符串相等 =false:两个字符串不相等
     */
    public static boolean equalsVal(String s1, String s2) {
        if (s1 == s2) return true;
        if (null == s1) return false;
        return s1.equals(s2);
    }

    /**
     * 判断字符串是否内容相同，不区分大小写
     *
     * @param s1 第1个输入字符串
     * @param s2 第2个输入字符串
     * @功能: 布尔值=true：两个字符串相等 =false:两个字符串不相等
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (null == s1) {
            return false;
        }
        return s1.equalsIgnoreCase(s2);
    }

    /**
     * 把字符数组转换成字符串
     *
     * @param array 字符数组
     * @功能: 转换后的字符串
     */
    public static String toString(char[] array) {
        return String.valueOf(array);
    }

    public static String toString(String[] array) {
        StringBuilder buff = new StringBuilder();
        if (null != array && array.length > 0) for (int i = 0; i < array.length; i++) {
            if (i > 0) buff.append(",");
            buff.append(array[i]);
        }
        return buff.toString();
    }

    /**
     * 在str字符串中，将所有token字符串，用delim字符串进行转义处理。
     *
     * @param str   被替换的字符串
     * @param token 被替换的子字符串
     * @param delim 子字符串需要替换的内容
     * @功能: 已经替换好的字符串
     */
    public static String normalize(String str, String token, String delim) {
        // 为空，直接返回
        if (empty(str)) {
            return "";
        }
        // 查找并替换
        StringTokenizer tokenizer = new StringTokenizer(str, token);
        StringBuilder fixedBuilder = new StringBuilder();
        while (tokenizer.hasMoreTokens()) {
            if (fixedBuilder.length() == 0) {
                fixedBuilder.append(tokenizer.nextToken());
            } else {
                fixedBuilder.append(fixedBuilder);
                fixedBuilder.append(delim);
                fixedBuilder.append(token);
                fixedBuilder.append(tokenizer.nextToken());
            }
        }
        if (str.indexOf(delim) == 0) {
            fixedBuilder.append(delim).append(token).append(fixedBuilder);
        }
        if (str.lastIndexOf(delim) == (str.length() - 1)) {
            fixedBuilder.append(fixedBuilder).append(delim).append(token);
        }
        return fixedBuilder.toString();
    }

    /**
     * 在字符串中，用新的字符串替换指定的字符
     *
     * @param src     需要替换的字符串
     * @param charOld 被替换的字符
     * @param strNew  用于替换的字符串
     * @功能: 已经替换好的字符串
     */
    public static String replace(String src, char charOld, String strNew) {
        if (null == src) {
            return src;
        }
        if (null == strNew) {
            return src;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0, n = src.length(); i < n; i++) {
            char c = src.charAt(i);
            if (c == charOld) {
                buf.append(strNew);
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    /**
     * 在字符对象中，用新的字符串替换指定的字符串
     *
     * @param src    需要替换的字符对象
     * @param strOld 被替换的字符串
     * @param strNew 用于替换的字符串
     */
    public static void replace(StringBuilder src, String strOld, String strNew) {
        if ((null == src) || (src.length() <= 0)) {
            return;
        }
        String s = replace(src.toString(), strOld, strNew);
        src.setLength(0);
        src.append(s);
    }

    /**
     * 在字符串中，用新的字符串替换指定的字符串
     *
     * @param src    需要替换的字符对象
     * @param strOld 被替换的字符串
     * @param strNew 用于替换的字符串
     * @功能: 已经被替换的字符串
     */
    public static String replace(String src, String strOld, String strNew) {
        if (null == src) {
            return src;
        }
        if (zero(strOld)) {
            return src;
        }
        if (null == strNew) {
            return src;
        }
        if (equalsVal(strOld, strNew)) {
            return src;
        }
        return src.replace(strOld, strNew);
    }

    /**
     * 把字符串的第一个字符变为大写
     *
     * @param s 输入字符串
     * @功能: 返回第一个字符是大写的字符串
     */
    public static String upperFirst(String s) {
        String str = null;
        if (null != s) {
            if (s.length() == 1) {
                str = s.toUpperCase();
            } else {
                str = s.substring(0, 1).toUpperCase() + s.substring(1);
            }
        }
        return str;
    }

    /**
     * 把字符串的第一个字符变为小写
     *
     * @param s 输入的字符串
     * @功能: 返回的第一个字符是小写的字符串
     */
    public static String lowerFirst(String s) {
        String str = null;
        if (null != s) {
            if (s.length() == 1) {
                str = s.toLowerCase();
            } else {
                str = s.substring(0, 1).toLowerCase() + s.substring(1);
            }
        }
        return str;
    }

    public static String lower(String s) {
        String str = null;
        if (null != s) {
            str = s.toLowerCase();
        }
        return str;
    }

    /**
     * 根据指定的delima标志获取输入字符串的后缀
     *
     * @param str    输入字符串
     * @param delima 指定的标志,一般是一个字符，如“.”
     * @功能: 输入字符串子的后缀
     */
    public static String getLastSuffix(String str, String delima) {
        if (zero(delima)) {
            return str;
        }
        String suffix = "";
        if (!zero(str)) {
            int index = str.lastIndexOf(delima);
            if (index >= 0) {
                suffix = str.substring(index + delima.length());
            } else {
                suffix = str;
            }
        }
        return suffix;
    }

    public static String getLastSuffix(String str, char delima) {
        String suffix = "";
        if (!zero(str)) {
            int index = str.indexOf(delima);
            if (index >= 0) {
                suffix = str.substring(index + 1);
            } else {
                suffix = str;
            }
        }
        return suffix;
    }

    /**
     * 根据指定的delima标志获取输入字符串的前缀
     *
     * @param src    输入字符串
     * @param delima 指定的标志,一般是一个字符，如“.”
     * @功能: 输入字符串的前缀
     */
    public static String getLastPrefix(String src, String delima) {
        if (zero(delima)) {
            return src;
        }
        String prefix = "";
        if (!zero(src)) {
            int index = src.lastIndexOf(delima);
            if (index >= 0) {
                prefix = src.substring(0, index);
            }
        }
        return prefix;
    }

    public static String getLastPrefix(String src, char delima) {
        String prefix = "";
        int index = src.indexOf(delima);
        if (index >= 0) {
            prefix = src.substring(0, index);
        }
        return prefix;
    }

    /**
     * 将str字符串按照其中出现的delim分割成字符串数组
     *
     * @param str   输入的字符串
     * @param delim 分割标志
     * @功能: 分割好的数组
     */
    public static String[] split(String str, String delim) {
        if (zero(str) || zero(delim)) {
            return new String[0];
        }
        return str.split(delim);
    }

    /**
     * 将str字符串按照其中出现的delim分割成字符串数组,并能去掉前后空格
     *
     * @param str   输入的字符串
     * @param delim 分割标志
     * @param trim  =true 去掉前后空格 =false 不去掉前后空格
     * @功能: 分割好的数组
     */
    public static String[] split(String str, String delim, boolean trim) {
        String[] set = split(str, delim);
        if (trim) {
            for (int i = 0; i < set.length; i++) {
                set[i] = set[i].trim();
            }
        }
        return set;
    }

    /**
     * 判断输入字符串是否包含指定的字符串
     *
     * @param str       输入字符串
     * @param searchStr 指定是否包含的字符串
     * @功能: =true:包含指定的字符串 =false:不包含指定的字符串
     */
    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        if (searchStr.length() == 0) // ""空串不认为被包含。String.indexOf()认为空串被包含
        {
            return false;
        } else {
            return str.indexOf(searchStr) >= 0;
        }
    }

    /**
     * 将set字符串数组从fromIndex开始以后的元素合并成以delim为分割符的字符串
     *
     * @param set
     * @param delim
     * @param fromIndex 以0开始
     * @功能:
     */
    public static String join(String[] set, String delim, int fromIndex) {
        if ((null == set) || (set.length <= 0) || (fromIndex >= set.length)) return "";
        if (fromIndex < 0) fromIndex = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(set[fromIndex]);
        for (int i = fromIndex + 1; i < set.length; i++) {
            sb.append(delim);
            sb.append(set[i]);
        }
        return sb.toString();
    }

    /**
     * 把占位符号进行替换.<br>
     * 工程名:<br>
     * 方法名:replaceSpecialChar方法.<br>
     *
     * @param replaceContentSrc :被替换的原字符串
     * @param inputPrifx        ：input输入框的名称前缀
     * @功能: 替换好的字符串
     * @author:Cui WenKe
     * @since :1.0:2009-8-8
     */
    public static StringBuilder replaceSpecialChar(String replaceContentSrc, String inputPrifx) {
        String oldReplaceContent = replaceContentSrc;
        StringBuilder builder = new StringBuilder();
        if (StringTool.empty(oldReplaceContent)) {
            return builder;
        }
        String splitChar = new String("_");
        String replaceStrBegin = "<input type=\"text\" class=\"inputUnderLine2\" name=\"" + inputPrifx;
        String replaceStrMiddle = "\" id=\"" + inputPrifx + "Id";
        String replaceStrend = "\">&nbsp;&nbsp;&nbsp;";
        // 首先判断开始有没有
        String beginChar = oldReplaceContent.substring(0, splitChar.length());
        if (StringTool.equalsVal(beginChar, splitChar)) {
            builder.append(replaceStrBegin + 0 + replaceStrMiddle + 0 + replaceStrend);
            oldReplaceContent = oldReplaceContent.substring(splitChar.length());
        }
        // 把中间的替换掉
        boolean flagReplace = false;
        String endChar = oldReplaceContent.substring(oldReplaceContent.length() - splitChar.length(), oldReplaceContent.length());
        if (StringTool.equalsVal(endChar, splitChar)) {
            oldReplaceContent = oldReplaceContent.substring(0, oldReplaceContent.length() - splitChar.length());
            flagReplace = true;
        }
        // 把中间的去掉
        String[] splitStrs = StringTool.split(oldReplaceContent, splitChar);
        if (flagReplace) {
            for (int i = 0; i < splitStrs.length; i++) {
                String q = splitStrs[i];
                builder.append(q);
                builder.append(replaceStrBegin + (i + 1) + replaceStrMiddle + (i + 1) + replaceStrend);
            }
        } else {
            for (int i = 0; i < splitStrs.length; i++) {
                String q = splitStrs[i];
                builder.append(q);
                if (i != splitStrs.length - 1) {
                    builder.append(replaceStrBegin + (i + 1) + replaceStrMiddle + (i + 1) + replaceStrend);
                }
            }
        }
        return builder;
    }

    /**
     * 指定字符串出现的次数.<br>
     * 工程名:<br>
     * 包名:<br>
     * 方法名:countStringNumber方法.<br>
     *
     * @param srcStr   ：查找的字符串
     * @param countStr ：指定要查找的字符串
     * @功能:
     * @author:Cui WenKe
     * @since :1.0:2009-8-10
     */
    public static int countStringNumber(String srcStr, String countStr) {
        int indexCount = 0;
        int index = 0;
        int count = 0;
        for (; ; ) {
            index = srcStr.indexOf(countStr, indexCount);
            if (index == -1) {
                break;
            }
            count++;
            indexCount = (index += countStr.length());
        }
        return count;
    }

    public static List<String> getListByStr(String inStr, String delim) {
        List<String> tempList = new ArrayList();
        if (!empty(inStr)) {
            String[] tempArr = split(inStr, delim);
            for (String str : tempArr) {
                tempList.add(str);
            }
        }
        return tempList;
    }

    /**
     * 为字符串前后加上单引号.<br>
     * 工程名:<br>
     * 包名:<br>
     * 方法名:toUpdateStr方法.<br>
     *
     * @param inStr
     * @功能:
     * @author:Cui WenKe
     * @since :1.0:2010-6-2
     */
    public static String toUpdateStr(String inStr) {
        if (zero(inStr)) {
            return "";
        }
        return "'" + inStr + "'";
    }
    /**
     *
     , 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
     * 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
     */
    /**
     * 对查询的条件字符串进行解析，通过空格解析出查询条件集合
     *
     * @param text 输入的查询关键字
     * @功能: 查询条件集合
     */
    public static List<String> parseKeys(String text) {
        // 新建一个集合装解析出来的查询关键字
        List<String> strList = new ArrayList();
        // 判断字符串是否有值
        if (text != null && text.trim() != null && text.trim().length() > 0) {
            // 在字符串后面加空格是为了下面好截取多个字符串成查询条件
            text = text + " ";
            // 将字符串转换成char数组
            char[] dst = text.toCharArray();
            // 新建一个字符串，拼接查询关键字
            String str = "";
            // 循环每个字节，如果不是空格就拼接成字符串
            for (int i = 0; i < dst.length; i++) {
                char temp = ' ';
                // 判断当前字符是否空格
                if (temp != dst[i]) {
                    // 如果当前字符不是空格就进行拼接
                    str = str + String.valueOf(dst[i]);
                } else {
                    // 如果当前字符是空格就判断当前集合中是否已经包含该关键字并且关键字不能为空
                    if (!strList.contains(str) && !str.equals("")) {
                        // 将满足条件的关键字装入集合中
                        strList.add(str);
                    }
                    str = "";
                }
            }
            strList.size();
        }
        return strList;
    }

    public static InputStream string2InputStream(String str) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(str.getBytes("UTF-8"));
    }

    public static String inputStream2String(InputStream in) throws IOException {
        InputStreamReader reader = new InputStreamReader(in, "UTF-8");
        BufferedReader br = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        if (null != reader) {
            reader.close();
        }
        return sb.toString();
    }

    /**
     * 根据传入格式返回对应日期的字符串表示
     *
     * @param format
     * @param date
     * @功能:
     */
    public static String getDateString(Date date, String format) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getDateString() {
        Date date = new Date();
        return getDateString(date, "yyyy-MM-dd");
    }

    /**
     * 根据默认格式返回对应日期的字符串表示
     *
     * @param date
     * @功能:
     */
    public static String getDateString(Date date) {
        return getDateString(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 生成指定长度的随机字符串
     *
     * @param Length
     * @功能:
     */
    public static String generateRandom(int Length) {
        StringBuilder newRandom = new StringBuilder(36);
        Random rd = new Random();
        for (int i = 0; i < Length; i++) {
            newRandom.append(constant[rd.nextInt(36)]);
        }
        return newRandom.toString();
    }

    /**
     * 生成唯一码：由年月日时分秒毫秒+15位随机字符串组成
     *
     * @功能:
     */
    public static String uuid() {
        StringBuilder buffer = new StringBuilder("");
        buffer.append(getDateString(new Date(), "yyyyMMddHHmmssSSS"));
        buffer.append(generateRandom(15));
        return buffer.toString();
    }

    /**
     * 生成短唯一码：由月日时分秒毫秒+7位随机字符串组成
     *
     * @功能:
     */
    public static String suuid() {
        StringBuilder buffer = new StringBuilder("");
        buffer.append(getDateString(new Date(), "MMddHHmmssSSS"));
        buffer.append(generateRandom(7));
        return buffer.toString();
    }
    // ------------------------public方法区------------------------
    // 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串 最重要的一个方法，思路如下：一个个字符读入、判断、输出

    public static String cn2py(String SourceStr) {
        String Result = "";
        int StrLength = SourceStr.length();
        int i;
        try {
            for (i = 0; i < StrLength; i++) {
                Result += Char2Initial(SourceStr.charAt(i));
            }
        } catch (Exception e) {
            Result = "";
        }
        return Result;
    }
    // ------------------------private方法区------------------------

    /**
     * 输入字符,得到他的声母,英文字母返回对应的大写字母,其他非简体汉字返回 '0' 　　*
     */
    private static char Char2Initial(char ch) throws UnsupportedEncodingException {
        // 对英文字母的处理：小写字母转换为大写，大写的直接返回
        if (ch >= 'a' && ch <= 'z') {
            return (char) (ch - 'a' + 'A');
        }
        if (ch >= 'A' && ch <= 'Z') {
            return ch;
        }
        // 对非英文字母的处理：转化为首字母，然后判断是否在码表范围内，
        // 若不是，则直接返回。
        // 若是，则在码表内的进行判断。
        int gb = gbValue(ch);// 汉字转换首字母
        if ((gb < BEGIN) || (gb > END))// 在码表区间之前，直接返回
        {
            return ch;
        }
        int i;
        for (i = 0; i < 26; i++) {// 判断匹配码表区间，匹配到就break,判断区间形如“[,)”
            if ((gb >= table[i]) && (gb < table[i + 1])) {
                break;
            }
        }
        if (gb == END) {// 补上GB2312区间最右端
            i = 25;
        }
        return initialtable[i]; // 在码表区间中，返回首字母
    }

    /**
     * 取出汉字的编码 cn 汉字
     */
    private static int gbValue(char ch) throws UnsupportedEncodingException {// 将一个汉字（GB2312）转换为十进制表示。
        String str = String.valueOf(ch);
        byte[] bytes = str.getBytes("GB2312");
        if (bytes.length < 2) {
            return 0;
        }
        return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
    }
}
