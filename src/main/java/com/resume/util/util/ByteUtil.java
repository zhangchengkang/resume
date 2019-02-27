/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 * <p>
 *  @接口方法说明: ByteUtil.java
 *  @Prject: kingtopware-common
 *  @Package: com.kingtopware.common.util
 *  @注意事项: 自定义base64位普通加密算法
 *  @author: chengjin
 *  @date: 2016年12月1日 下午4:31:12
 *  @version: V1.0  
 *  
 */
package com.resume.util.util;

import java.io.UnsupportedEncodingException;

/**
 * @author chengjin
 * @注意事项 字节转float相关方法存在BUG，不建议使用
 */
public class ByteUtil {
    public static final String CHARSET_NAME = "UTF-8";
    public static final String BYTE_NULL = "Bytes Array is null!";
    private static final String ARG_OFF = "arg off is";
    private static final String LIMIT_LENGTH = " it's must bigger than 0 !";
    private static final String BYTE_ARRAY_LENGTH = "Bytes Array'length is ";
    private static final String BYTE_ARRAY_LENGTH_EXPECTED = " ,it's length is less than expected at leasted ";

    private ByteUtil() {
        throw new UnsupportedOperationException("Not allow construct!");
    }

    /**
     * 判断字节数组某个值是否为1
     *
     * @param b
     * @param off
     * @throws NullPointerException
     * @功能:
     * @Exception IndexOutOfBoundsException
     */
    public static boolean byte2Boolean(byte[] b, int off) throws NullPointerException, IndexOutOfBoundsException {
        return b[off] != 0;
    }

    /**
     * 字节数组中连续取两个字节转char
     *
     * @param b
     * @param off
     * @功能 第一个byte转换为高8位，第二个字节转换为低8位
     */
    public static char byte2Char(byte[] b, int off) {
        if (b == null) {
            throw new NullPointerException(BYTE_NULL);
        }
        if (off < 0) {
            throw new IndexOutOfBoundsException(ARG_OFF + off + LIMIT_LENGTH);
        }
        if (b.length < 2 + off) {
            throw new IndexOutOfBoundsException(BYTE_ARRAY_LENGTH + b.length + BYTE_ARRAY_LENGTH_EXPECTED + (2 + off) + "!");
        }
        return (char) ((b[off + 1] & 0xFF) + ((b[off + 0]) << 8));
    }

    /**
     * 字节数组值转short
     *
     * @param b
     * @param off
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException * @功能:
     */
    public static short byte2Short(byte[] b, int off) throws IndexOutOfBoundsException, NullPointerException {
        if (b == null) {
            throw new NullPointerException("Bytes Array is null!");
        }
        if (off < 0) {
            throw new IndexOutOfBoundsException("arg off is" + off + " it's must bigger than 0 !");
        }
        if (b.length < 2 + off) {
            throw new IndexOutOfBoundsException("Bytes Array'length is " + b.length + " ,it's length is less than expected at leasted " + (2 + off) + "!");
        }
        return (short) ((b[off + 1] & 0xFF) + ((b[off + 0]) << 8));
    }

    /**
     * 字节数组连续取4位转int
     *
     * @param b
     * @param off
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     * @功能:
     */
    public static int byte2Int(byte[] b, int off) throws NullPointerException, IndexOutOfBoundsException {
        if (b == null) {
            throw new NullPointerException("Bytes Array is null!");
        }
        if (off < 0) {
            throw new IndexOutOfBoundsException("arg off is" + off + " it's must bigger than 0 !");
        }
        if (b.length < 4 + off) {
            throw new IndexOutOfBoundsException("Bytes Array'length is " + b.length + " ,it's length is less than expected at leasted " + (4 + off) + "!");
        }
        return (b[off + 3] & 0xFF) + ((b[off + 2] & 0xFF) << 8) + ((b[off + 1] & 0xFF) << 16) + ((b[off + 0]) << 24);
    }

    /**
     * 字节数组连续取4位float
     *
     * @param b
     * @param off
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     * @功能:
     */
    public static float byte2Float(byte[] b, int off) throws NullPointerException, IndexOutOfBoundsException {
        if (b == null) {
            throw new NullPointerException("Bytes Array is null!");
        }
        if (off < 0) {
            throw new IndexOutOfBoundsException("arg off is" + off + " it's must bigger than 0 !");
        }
        if (b.length < 4 + off) {
            throw new IndexOutOfBoundsException("Bytes Array'length is " + b.length + " ,it's length is less than expected at leasted " + (4 + off) + "!");
        }
        int i = (b[off + 3] & 0xFF) + ((b[off + 2] & 0xFF) << 8) + ((b[off + 1] & 0xFF) << 16) + ((b[off + 0]) << 24);
        return Float.intBitsToFloat(i);
    }

    /**
     * 字节数组值转long
     *
     * @param b
     * @param off
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     * @功能:
     */
    public static long byte2Long(byte[] b, int off) throws NullPointerException, IndexOutOfBoundsException {
        if (b == null) {
            throw new NullPointerException("Bytes Array is null!");
        }
        if (off < 0) {
            throw new IndexOutOfBoundsException("arg off is" + off + " it's must bigger than 0 !");
        }
        if (b.length < 8 + off) {
            throw new IndexOutOfBoundsException("Bytes Array'length is " + b.length + " ,it's length is less than expected at leasted " + (8 + off) + "!");
        }
        return (b[off + 7] & 0xFFL) + ((b[off + 6] & 0xFFL) << 8) + ((b[off + 5] & 0xFFL) << 16) + ((b[off + 4] & 0xFFL) << 24) + ((b[off + 3] & 0xFFL) << 32) + ((b[off + 2] & 0xFFL) << 40) + ((b[off + 1] & 0xFFL) << 48) + (((long) b[off
                + 0]) << 56);
    }

    /**
     * 字节数组值转double
     *
     * @param b
     * @param off
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     * @功能:
     */
    public static double byte2Double(byte[] b, int off) throws NullPointerException, IndexOutOfBoundsException {
        if (b == null) {
            throw new NullPointerException("Bytes Array is null!");
        }
        if (off < 0) {
            throw new IndexOutOfBoundsException("arg off is" + off + " it's must bigger than 0 !");
        }
        if (b.length < 8 + off) {
            throw new IndexOutOfBoundsException("Bytes Array'length is " + b.length + " ,it's length is less than expected at leasted " + (8 + off) + "!");
        }
        long j = (b[off + 7] & 0xFFL) + ((b[off + 6] & 0xFFL) << 8) + ((b[off + 5] & 0xFFL) << 16) + ((b[off + 4] & 0xFFL) << 24) + ((b[off + 3] & 0xFFL) << 32) + ((b[off + 2] & 0xFFL) << 40) + ((b[off + 1] & 0xFFL) << 48) + (((long)
                b[off + 0]) << 56);
        return Double.longBitsToDouble(j);
    }

    /**
     * 字节数组值转String
     *
     * @param b
     * @param off
     * @throws UnsupportedEncodingException
     * @throws NullPointerException
     * @throws IndexOutOfBoundsException
     * @功能:
     * @注意事项 前4字节为字符串所占的长度
     */
    public static String byte2String(byte[] b, int off) throws UnsupportedEncodingException, NullPointerException, IndexOutOfBoundsException {
        if (b == null) {
            throw new NullPointerException("Bytes Array is null!");
        }
        if (off < 0) {
            throw new IndexOutOfBoundsException("arg off is" + off + " it's must bigger than 0 !");
        }
        String val = null;
        int len = byte2Int(b, off);
        if (len >= 0) {
            byte[] bs = new byte[len];
            System.arraycopy(b, 4 + off, bs, 0, len);
            val = new String(bs, CHARSET_NAME);
        }
        return val;
    }

    /**
     * 将布尔值转成0或者1
     *
     * @param val
     * @功能:
     */
    public static byte[] boolean2Byte(boolean val) {
        byte[] b = new byte[1];
        b[0] = (byte) (val ? 1 : 0);
        return b;
    }

    /**
     * char转字节数组
     *
     * @param b
     * @param off
     * @功能:
     * @注意事项 藏文 ཤོར 无法用char标识
     */
    public static byte[] char2Byte(char val) {
        byte[] b = new byte[2];
        b[1] = (byte) (val >>> 0);
        b[0] = (byte) (val >>> 8);
        return b;
    }

    /**
     * short转字节数组
     *
     * @param b
     * @param off
     * @功能:
     */
    public static byte[] short2Byte(short val) {
        byte[] b = new byte[2];
        b[1] = (byte) (val >>> 0);
        b[0] = (byte) (val >>> 8);
        return b;
    }

    /**
     * int转字节数组
     *
     * @param b
     * @param off
     * @功能:
     */
    public static byte[] int2Byte(int val) {
        byte[] b = new byte[4];
        b[3] = (byte) (val >>> 0);
        b[2] = (byte) (val >>> 8);
        b[1] = (byte) (val >>> 16);
        b[0] = (byte) (val >>> 24);
        return b;
    }

    /**
     * float转字节数组
     *
     * @param val
     * @功能:
     */
    public static byte[] float2Byte(float val) {
        byte[] b = new byte[4];
        int i = Float.floatToIntBits(val);
        b[3] = (byte) (i >>> 0);
        b[2] = (byte) (i >>> 8);
        b[1] = (byte) (i >>> 16);
        b[0] = (byte) (i >>> 24);
        return b;
    }

    /**
     * long转字节数组
     *
     * @param val
     * @功能:
     */
    public static byte[] long2Byte(long val) {
        byte[] b = new byte[8];
        b[7] = (byte) (val >>> 0);
        b[6] = (byte) (val >>> 8);
        b[5] = (byte) (val >>> 16);
        b[4] = (byte) (val >>> 24);
        b[3] = (byte) (val >>> 32);
        b[2] = (byte) (val >>> 40);
        b[1] = (byte) (val >>> 48);
        b[0] = (byte) (val >>> 56);
        return b;
    }

    /**
     * double转字节数组
     *
     * @param val
     * @功能:
     */
    public static byte[] double2Byte(double val) {
        byte[] b = new byte[8];
        long j = Double.doubleToLongBits(val);
        b[7] = (byte) (j >>> 0);
        b[6] = (byte) (j >>> 8);
        b[5] = (byte) (j >>> 16);
        b[4] = (byte) (j >>> 24);
        b[3] = (byte) (j >>> 32);
        b[2] = (byte) (j >>> 40);
        b[1] = (byte) (j >>> 48);
        b[0] = (byte) (j >>> 56);
        return b;
    }

    /**
     * string转字节数组并记录数组长度
     *
     * @param val
     * @throws UnsupportedEncodingException
     * @功能:
     * @注意事项 前四字节保存字符串字节数组的长度
     */
    public static byte[] string2Byte(String val) throws UnsupportedEncodingException {
        byte[] b = null;
        byte[] tmp = null;
        if (val != null) {
            tmp = val.getBytes(CHARSET_NAME);
            b = new byte[4 + tmp.length];
            System.arraycopy(int2Byte(tmp.length), 0, b, 0, 4);
            System.arraycopy(tmp, 0, b, 4, tmp.length);
        } else {
            b = int2Byte(-999);
        }
        return b;
    }

    /**
     * 将字节数组转成对应字符串二进制形式
     *
     * @param b
     * @功能:
     */
    public static String toBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder(8);
        for (int j = 0; j < b.length; j++) {
            for (int i = 7; i >= 0; i--) {
                int t = 1 << i;
                sb.append((b[j] & t) >> i);
            }
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 将字节数组输出为字符串的16进制形式
     *
     * @param bs
     * @功能:
     */
    public static String display(byte[] bs) {
        if (bs == null) return null;
        StringBuilder strHEX = new StringBuilder(16);
        for (int i = 0; i < bs.length; i++) {
            strHEX.append(Integer.toHexString(bs[i] >> 4 & 0xf));
            strHEX.append(Integer.toHexString(bs[i] & 0xf));
            strHEX.append(' ');
            if (i % 16 == 15) strHEX.append(System.getProperty("line.separator"));
        }
        return strHEX.toString();
    }
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(byte2Boolean(boolean2Byte(true), 0));
//        System.out.println(byte2Char(char2Byte('x'), 0));
//        System.out.println(byte2Short(short2Byte((short) 123), 0));
//        System.out.println(byte2Int(int2Byte(88), 0));
//        float b = 9787101.21F;
//        System.out.println(byte2Float(float2Byte(b), 0));
//        System.out.println(byte2Long(long2Byte(9999999999999999L), 0));
//        System.out.println(byte2Double(double2Byte(100), 0));
//        System.out.println(byte2String(string2Byte(""), 0));
//        System.out.println(byte2String(string2Byte("东莞刮起微博旋风"), 0));
//        System.out.println(byte2String(string2Byte(null), 0));
//        System.out.println(toBinaryString(int2Byte(2)));
//    }
}
