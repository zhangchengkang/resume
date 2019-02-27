/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 * <p>
 *  @接口方法说明: FileUtils.java
 *  @Prject: kingtopware-common
 *  @Package: com.kingtopware.common.util
 *  @注意事项: 关于异常的工具类
 *  @author: chengjin  
 *  @date: 2016年12月1日 下午4:31:12
 *  @version: V1.0  
 *  
 */
package com.resume.util.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class FileUtils {
    final static String SPLIT_SYMBOL = ".";
    /**
     * 系统默认换行符
     */
    private final static String LINE_BREAK = "\n";
    /**
     * windows系统换行符\r\n
     */
    private final static String OVERLINE_BREAK = "\r\n";
    /**
     * 每行文件前部4个字节存放本行数据长度
     */
    private final static int NUMER = 4;
    private final static String FILE_NULL = "file is null";
    private final static String DIR_NULL = "文件夹路径不能为空";

    private FileUtils() {
        throw new UnsupportedOperationException("Not allow construct!");
    }

    /**
     * 按照指定编码写入数据到文件
     *
     * @param path     完整文件路径
     * @param content  内容
     * @param encoding 编码
     * @param ifAdd    是否追加模式
     * @throws FileNotFoundException,IOException
     */
    public static void write(String path, String content, String encoding, boolean ifAdd) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException(path);
        }
        try (FileOutputStream fos = new FileOutputStream(file, ifAdd)) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, encoding))) {
                writer.write(content);
                writer.close();
                fos.close();
            }
        }
    }

    /**
     * 按指定编码按行读取文件
     *
     * @param path
     * @param encoding
     * @throws FileNotFoundException,IOException
     * @功能:
     */
    public static String read(String path, String encoding) throws IOException {
        StringBuilder content = new StringBuilder();
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException(path);
        }
        try (FileInputStream fileStream = new FileInputStream(file)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream, encoding))) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    content.append(line + LINE_BREAK);
                }
            }
        }
        return content.toString();
    }

    /**
     * 典型方式读取文件
     *
     * @param filename
     * @throws FileNotFoundException,IOException
     * @功能:
     */
    public static byte[] read(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(f))) {
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        }
    }

    /**
     * NIO way
     *
     * @param filename
     * @throws FileNotFoundException,IOException
     * @功能:
     */
    public static byte[] toByteArray2(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }
        FileChannel channel = null;
        try (FileInputStream fs = new FileInputStream(f)) {
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        }
    }

    /**
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @param filename
     * @throws FileNotFoundException,IOException
     * @功能:
     */
    public static byte[] readByteBuffer(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException(filename);
        }
        try (RandomAccessFile rf = new RandomAccessFile(filename, "r"); FileChannel fc = rf.getChannel();) {
            MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        }
    }

    /**
     * 将文件读取拼成字符串
     *
     * @param file
     * @throws NullPointerException,FileNotFoundException,IOException
     * @功能:
     */
    public static String read(File file) throws NullPointerException, IOException {
        if (file == null) {
            throw new NullPointerException(FILE_NULL);
        }
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
        StringBuilder result = new StringBuilder();
        String s = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((s = br.readLine()) != null) {
                result.append("\n" + s);
            }
            return result.toString();
        }
    }

    /**
     * 在一整行数据前加入字节长度计数后返回,处理换行符号属于系统自动追加的情况
     *
     * @param str 默认行数据不带换行符，此时需要追加换行符的长度(多一个字节'\n'的长度)
     * @throws UnsupportedEncodingException
     * @功能:
     */
    public static String tranStringNOLineBreak(String str) throws UnsupportedEncodingException {
        return tranStringNOLineBreak(str, 0, "utf-8");
    }

    /**
     * 在一整行数据前加入字节长度计数后返回,处理换行符号属于系统自动追加的情况
     *
     * @param str
     * @param type=2代表换行符为\r\n，其他数值\n换行符
     * @param encode                     编码格式 默认行数据不带换行符，此时需要追加换行符的长度
     * @throws UnsupportedEncodingException
     * @功能:
     */
    public static String tranStringNOLineBreak(String str, int type, String encode) throws UnsupportedEncodingException {
        byte[] source = str.getBytes();
        int lineLength = LINE_BREAK.getBytes().length;
        if (type == 2) {
            lineLength = OVERLINE_BREAK.getBytes().length;
        }
        byte[] des = new byte[source.length + NUMER];
        // 存入带换行符的长度，实际数据不存入
        byte[] len = ByteUtil.int2Byte(source.length + NUMER + lineLength);
        // 拷贝实际数据
        System.arraycopy(source, 0, des, NUMER, source.length);
        // 拷贝长度
        System.arraycopy(len, 0, des, 0, NUMER);
        return new String(des, encode);
    }

    /**
     * 在一整行数据前加入字节长度计数后返回
     *
     * @param str
     * @param encode 指定编码
     * @throws UnsupportedEncodingException
     * @功能:
     */
    public static String tranString(String str, String encode) throws UnsupportedEncodingException {
        byte[] source = str.getBytes();
        byte[] des = new byte[source.length + NUMER];
        byte[] len = ByteUtil.int2Byte(source.length);
        // 拷贝实际数据
        System.arraycopy(source, 0, des, NUMER, source.length);
        // 拷贝长度
        System.arraycopy(len, 0, des, 0, NUMER);
        return new String(des, encode);
    }

    /**
     * 在一整行数据前加入字节长度计数后返回
     *
     * @param str
     * @param containLineBreak 是否包含换行符,不包含换行符则自动增加换行符'\n'
     * @throws UnsupportedEncodingException
     * @功能:
     */
    public static String tranString(String str, boolean containLineBreak) throws UnsupportedEncodingException {
        return tranString(containLineBreak ? str : LINE_BREAK + str, "utf-8");
    }

    /**
     * 在一整行数据前加入字节长度计数后返回
     *
     * @param str
     * @param encode           指定编码
     * @param containLineBreak 是否包含换行符,true代表已包含换行符，不需要处理
     * @throws UnsupportedEncodingException
     * @功能:
     */
    public static String tranString(String str, String encode, boolean containLineBreak) throws UnsupportedEncodingException {
        return tranString(containLineBreak ? str : LINE_BREAK + str, encode);
    }

    public static byte[] tranByte(byte[] source, boolean containLineBreak) {
        if (!containLineBreak) {
            byte[] lineBreaks = LINE_BREAK.getBytes();
            byte[] des = new byte[source.length + NUMER + lineBreaks.length];
            byte[] dataLengthByte = ByteUtil.int2Byte(source.length + lineBreaks.length);
            // 拷贝数据
            System.arraycopy(source, 0, des, NUMER, source.length);
            // 拷贝长度
            System.arraycopy(dataLengthByte, 0, des, 0, NUMER);
            // 拷贝换行符
            System.arraycopy(lineBreaks, 0, des, source.length, lineBreaks.length);
            return des;
        }
        return tranByte(source);
    }

    public static byte[] tranByte(byte[] source) {
        byte[] des = new byte[source.length + NUMER];
        byte[] len = ByteUtil.int2Byte(source.length);
        // 拷贝实际数据
        System.arraycopy(source, 0, des, NUMER, source.length);
        // 拷贝长度
        System.arraycopy(len, 0, des, 0, NUMER);
        return des;
    }

    /**
     * 拷贝文件,同时在每行文件起始位置前4字节写入本行长度
     *
     * @param sourcePath
     * @param dirPath
     * @throws FileNotFoundException,IOException
     */
    public static void tranFile(String sourcePath, String dirPath) throws IOException {
        File file = new File(sourcePath);
        if (!file.exists()) {
            throw new FileNotFoundException(sourcePath);
        }
        try (InputStream in = new FileInputStream(file); BufferedReader br = new BufferedReader(new InputStreamReader(in)); FileOutputStream fout = new FileOutputStream(dirPath);
             // 缓存字节输入流
             BufferedOutputStream bout = new BufferedOutputStream(fout);) {
            String bs = null;
            while ((bs = br.readLine()) != null) {
                byte[] source = (bs + LINE_BREAK).getBytes();
                byte[] dir = new byte[source.length + NUMER];
                byte[] len = ByteUtil.int2Byte(source.length);
                // 拷贝实际数据
                System.arraycopy(source, 0, dir, NUMER, source.length);
                // 拷贝长度
                System.arraycopy(len, 0, dir, 0, NUMER);
                bout.write(dir);
                bout.flush();
            }
        }
    }

    /**
     * 根据路径创建文件夹
     *
     * @param dirPath
     * @throws NullPointerException
     */
    public static void mkDirs(String dirPath) throws NullPointerException {
        mkDirs(dirPath, true);
    }

    /**
     * 根据路径创建文件夹
     *
     * @param dirPath
     */
    public static void mkDirs(String dirPath, boolean throwsAble) {
        if (throwsAble && StringUtil.isNull(dirPath)) {
            throw new NullPointerException(DIR_NULL);
        }
        File f = new File(dirPath);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * 删除指定路径的文件
     *
     * @param dirPath
     * @throws NullPointerException
     */
    public static boolean deleteDir(String dirPath) throws NullPointerException {
        if (StringUtil.isNull(dirPath)) {
            throw new NullPointerException(DIR_NULL);
        }
        File f = new File(dirPath);
        if (f.exists()) {
            return f.delete();
        }
        return true;
    }

    /**
     * 递归删除文件
     *
     * @param file
     * @功能:
     */
    public static boolean deleteAllFiles(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                if (!deleteAllFiles(child)) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    /**
     * wenjian 扩展名是否为指定的扩展名
     *
     * @param fileName
     * @param suffix
     * @功能: 参数为空时返回false
     */
    public static boolean isSuffixMatchs(String fileName, String suffix) {
        if (StringUtil.isNull(fileName) || StringUtil.isNull(suffix)) {
            return false;
        }
        return suffix.equalsIgnoreCase(getFileSuffix(fileName));
    }

    /**
     * 获取文件的扩展名
     *
     * @param fileName
     * @功能: 文件名为空或无扩展名, 返回null值
     */
    public static String getFileSuffix(String fileName) {
        String returnStr = null;
        // 文件名不为null，包含'.'，并且扩展名不为null
        if (!StringUtil.isNull(fileName) && fileName.indexOf(SPLIT_SYMBOL) > -1 && !fileName.endsWith(".")) {
            return fileName.substring(fileName.lastIndexOf(SPLIT_SYMBOL) + 1);
        }
        return returnStr;
    }

    /**
     * 删除文件或者文件夹,文件夹删除，先递归删除子目录及子目录的文件、子文件，再删除文件夹本身，
     *
     * @param file
     * @功能: 全部删除成功返回true，其他返回false，不可恢复的事务操作
     * @注意事项 在windows、linux下均测试过加锁删除可以，root用户文件用ktw账号删除可以
     */
    public static boolean deleteFile(File file) {
        boolean flag = true;
        //文件为空或者不存在直接默认删除成功
        if (file == null || !file.exists()) {
            return flag;
        }
        if (file.isDirectory()) {
            File[] array = file.listFiles();
            if (array != null) {
                for (File f : array) {
                    //递归删除子目录及子目录文件
                    if (!deleteFile(f)) {
                        flag = false;
                    }
                }
            }
        }
        //删除失败
        if (!file.delete()) {
            flag = false;
        }
        return flag;
    }
}
