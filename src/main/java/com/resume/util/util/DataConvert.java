package com.resume.util.util;

import java.io.*;
import java.text.NumberFormat;

/**
 * 数据转换
 * Created by Cui WenKe on 2015/4/28.
 */
public class DataConvert {
    /**
     * 字节转换InputStream
     *
     * @param in
     * @return
     */
    public static InputStream byteTOInputStream(byte[] in) throws Exception {
        ByteArrayInputStream is = new ByteArrayInputStream(in);
        return is;
    }

    /**
     * file 转换  InputStream
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static InputStream fileTOInputStream(File file) throws Exception {
        return byteTOInputStream(fileToBytes(file));
    }

    /**
     * inStream 转换 byte
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] inputStreamToBytes(InputStream inStream) throws Exception {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            bytes = swapStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage(), e);
        }
        return bytes;
    }

    /**
     * file 转换 byte
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] fileToBytes(File file) throws Exception {
        byte[] bytes = null;
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            bytes = inputStreamToBytes(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage(), e);
        }
        return bytes;
    }

    /**
     * 数组转换成十六进制字符串
     *
     * @return HexString
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制字符转字节
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * @param size
     * @return
     */
    public static String convertSize(long size) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        String result = String.valueOf(size);
        if (size < 1024) {
            result = result + " B";
        } else if (size < 1024 * 1024) {
            result = nf.format(size / 1024.0) + " KB";
        } else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
            result = nf.format(size / 1024.0 / 1024.0) + " MB";
        } else if (size >= 1024 * 1024 * 1024) {
            result = nf.format(size / 1024.0 / 1024.0 / 1024.0) + " GB";
        }
        return result;
    }

    public static long convertToGB(long size) {
        return size / 1024 / 1024 / 1024;
    }
}
