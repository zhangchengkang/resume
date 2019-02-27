/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 * <p>
 *  @接口方法说明: HashValue.java
 *  @Prject: kingtopware-common
 *  @Package: com.kingtopware.common.util
 *  @注意事项: 生成hash值的工具
 *  @author: chengjin  
 *  @date: 2016年12月1日 下午4:31:12
 *  @version: V1.0  
 *  
 */
package com.resume.util.util;

public class HashValue {
    /**
     * 生成hash码的第一位素数
     */
    final static int HASH_CODE_ONE = 13;
    /**
     * 生成hash码的第二位素数
     */
    final static int HASH_CODE_TWO = 31;
    /**
     * 生成hash码的第三位素数
     */
    final static int HASH_CODE_THREE = 17;
    /**
     * 默认空字符串
     */
    final static String NULL_STRING = "";

    private HashValue() {
        throw new UnsupportedOperationException("Not allow construct!");
    }

    /**
     * 将输入的字符串散列成16进制的字符串
     *
     * @param valueStr
     * @throws NullPointerException
     * @功能:
     */
    public static String compStringValue(String valueStr) throws NullPointerException {
        if (null == valueStr) {
            throw new NullPointerException();
        }
        long value = (long) valueStr.hashCode() * HASH_CODE_ONE;
        return Long.toHexString(value);
    }

    /**
     * 将输入的双字符串散列成16进制的字符串
     *
     * @throws NullPointerException
     **/
    public static String comp2StringValue(String value1, String value2) throws NullPointerException {
        if (null == value1) {
            throw new NullPointerException("parm value1 is null!");
        }
        if (null == value2) {
            throw new NullPointerException("parm value2 is null!");
        }
        long value = (long) value1.hashCode() * HASH_CODE_ONE + value2.hashCode() * HASH_CODE_TWO;
        return Long.toHexString(value);
    }

    /**
     * 根据三个字符串生成惟一hash标识符
     *
     * @param value1
     * @param value2
     * @param value3
     * @throws NullPointerException
     * @功能: 返回16进制字符串格式hash码
     */
    public static String comp3StringValue(String value1, String value2, String value3) throws NullPointerException {
        if (null == value1) {
            throw new NullPointerException("parm value1 is null!");
        }
        if (null == value2) {
            throw new NullPointerException("parm value2 is null!");
        }
        if (null == value3) {
            throw new NullPointerException("parm value3 is null!");
        }
        return Long.toHexString((long) value1.hashCode() * HASH_CODE_ONE + value2.hashCode() * HASH_CODE_TWO + value3.hashCode() * HASH_CODE_THREE);
    }
//    public static void main(String[] args) {
//        System.out.println(compStringValue(null));
//        System.out.println(comp2StringValue("21", "/ba/"));
//    }
}
