package com.resume.util.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName: FileTool
 * @注意事项: 文件工具类
 * @author: ZhuLing
 * @date: 2017/6/30
 */
public class FileTool {
    /**
     * @param path    文件目录
     * @param oldName 原来的文件名
     * @param newName 新文件名
     * @功能: boolean
     * @注意事项: 文件重命名
     * @接口方法说明: renameFile
     */
    public static boolean renameFile(String path, String oldName, String newName) {
        //传入参数检查
        if (StringUtil.isNull(path) || StringUtil.isNull(oldName) || StringUtil.isNull(newName)) {
            return false;
        }
        if (!oldName.equals(newName)) {
            //新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(path + "/" + oldName);
            File newfile = new File(path + "/" + newName);
            if (!oldfile.exists()) {
                return false;//重命名文件不存在
            }
            //若在该目录下已经有一个文件和新文件名相同，则不允许重命名
            return !newfile.exists() && oldfile.renameTo(newfile);
        } else {//新文件名和旧文件名相同
            return false;
        }
    }

    /**
     * @param zipFile    zip文件
     * @param sourceFile 需要压缩的文件
     * @throws IOException
     * @功能: boolean
     * @注意事项: 文件压缩成zip
     * @接口方法说明: fileToZip
     */
    public static boolean fileToZip(File zipFile, File sourceFile) throws IOException {
        if (!sourceFile.exists()) {//待压缩的文件目录不存在
            return false;
        } else {
            try (FileOutputStream fos = new FileOutputStream(zipFile); ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));) {
                //进行文件压缩
                try {
                    fileToZip(zos, sourceFile, sourceFile.getName());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
    }

    /**
     * @param zos        zip文件输出流
     * @param sourceFile 需要压缩的源文件
     * @param fileName   源文件的名称
     * @throws IOException
     * @注意事项: 压缩成zip文件
     * @接口方法说明: fileToZip
     */
    private static void fileToZip(ZipOutputStream zos, File sourceFile, String fileName) throws IOException {
        if (sourceFile.isDirectory()) {
            File[] files = sourceFile.listFiles();
            if (null == files || files.length < 1) {
                zos.putNextEntry(new ZipEntry(fileName + "/")); // 创建空文件夹
            } else {
                for (File file : files) {
                    fileToZip(zos, file, fileName + "/" + file.getName()); // 递归遍历子文件夹
                }
            }
        } else {
            try (FileInputStream in = new FileInputStream(sourceFile); BufferedInputStream bis = new BufferedInputStream(in);) {
                zos.putNextEntry(new ZipEntry(fileName));
                int count;
                byte[] buff = new byte[1024 * 10];
                while ((count = bis.read(buff, 0, buff.length)) != -1) {
                    zos.write(buff, 0, count);// 将字节流写入当前zip目录
                }
            }
        }
    }

    /**
     * @param response
     * @param filePath    需要下载的文件绝对路径
     * @param contentType 返回实体类型
     * @param realName    下载文件的名称
     * @功能: boolean
     * @注意事项: http文件下载
     * @接口方法说明: download
     */
    public static boolean download(HttpServletResponse response, String filePath, String contentType, String realName) throws IOException {
        if (StringUtil.isNull(filePath) || StringUtil.isNull(realName)) {//传入的参数为空
            return false;
        }
        File file = new File(filePath);
        if (!file.exists()) {//源文件不存在或已删除
            return false;
        }
        long fileLength = file.length();
        response.setContentType(contentType);
        response.setCharacterEncoding("gbk");
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(realName, "UTF-8"));
        response.setHeader("Content-Length", String.valueOf(fileLength));
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file)); BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());) {
            byte[] buff = new byte[2048];
            int len;
            while ((len = bis.read(buff, 0, buff.length)) != -1) {
                bos.write(buff, 0, len);
            }
            return true;
        }
    }
}