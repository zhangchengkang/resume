/**
 * Copyright © 2017湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: HttpRequestUtil
 * @Prject: server-project
 * @Package: com.kingtopware.common.util
 * @ClassName: HttpRequestUtil
 * @注意事项: HTTP请求工具类
 * @author: Cui WenKe
 * @date: 2017/7/24
 * @version: V1.0
 */
package com.resume.util.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequestUtil {
    private HttpRequestUtil() {
        throw new UnsupportedOperationException("Not allow construct!");
    }

    public static String sendGet(String url) {
        return sendGet(url, "");
    }

    public static URLConnection getURLConnection(URL realUrl) throws IOException {
        URLConnection conn = realUrl.openConnection();
        // 设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
        conn.setRequestProperty("User-Operation-Info", "a3UjjlaLC9He");
        conn.setRequestProperty("Authorization", "Basic YWRtaW46Z2Vvc2VydmVy");
        return conn;
    }

    /**
     * @param url
     * @param param
     * @throws IOException
     * @功能:
     * @注意事项: 发送GET请求
     * @接口方法说明: sendGet
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            if (!StringUtil.isNull(param)) url += "?" + param;
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = getURLConnection(realUrl);
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static int getResponseCode(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.connect();
            return conn.getResponseCode();
        } catch (Exception e) {
            return 39006;
        }
    }

    /**
     * @param url
     * @param param
     * @功能:
     * @注意事项: 发送POST请求
     * @接口方法说明: sendPost
     * @see: com.kingtopware.common.util.HttpRequestUtil#sendPost()
     */
    public static String sendPost(String url, Object param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = getURLConnection(realUrl);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            //使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * @param url
     * @param param
     * @return
     * @接口方法说明: 发送http请求（解决请求云盘接口发送中文失败问题）
     * @功能: sendPostOfCloudDisk
     */
    public static String sendPostOfCloudDisk(String url, Object param) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = getURLConnection(realUrl);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param.toString());
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            //使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
