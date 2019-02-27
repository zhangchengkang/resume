/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 * <p>
 *  @接口方法说明: KtwListUtil.java
 *  @Prject: kingtopware-common
 *  @Package: com.kingtopware.common.util
 *  @注意事项:输入加载类的相对文件名路径，读取对应属性内容,暂不支持同一路径文件名
 *  @author: chengjin  
 *  @date: 2016年12月1日 下午4:31:12
 *  @version: V1.0  
 *  
 */
package com.resume.util.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropUtil {
    private static Properties properties = null;

    public static Properties getProperties() {
        if (null == properties) {
            try {
                InputStream inputStream = new FileInputStream(new File(catalinaHome() + "/bizrest/resources/config.properties"));
                properties = ObjectUtil.getPropertiesInfo(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    public static String catalinaHome() {
        return System.getProperty("catalina.home");
    }

    public static String catalinaHomeTemp() {
        return catalinaHome() + "/temp/";
    }

    /**
     * 读取时间间隔
     */
    private static final int interval = 1000 * 60 * 24;
    private static Map<URL, Properties> map = new ConcurrentHashMap<URL, Properties>();
    private static Thread thread = new Thread() {
        public void run() {
            while (true) {
                try {
                    if (map != null && !map.isEmpty()) {
                        for (URL config : map.keySet())
                            init(config);
                    }
                    Thread.sleep(interval);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    static {
        thread.start();
    }

    private PropUtil() {
    }

    /**
     * 在指定文件名中查找对应属性的值，没有值时返回默认值
     *
     * @param config
     * @param key
     * @param defaults
     * @功能:
     */
    public static String getProp(String config, String key, String defaults, Class<?> classz) {
        String value = getProp(config, key, classz);
        if (value == null) value = defaults;
        return value;
    }

    /**
     * 在指定文件名中查找对应属性的值，没有值时返回null
     *
     * @param config
     * @param key
     * @功能:
     */
    public static String getProp(String config, String key, Class<?> classz) {
        String value = null;
        //去掉空格
        if (config != null && !config.trim().equals("") && key != null && !key.trim().equals("")) {
            if (!config.contains(".")) config += ".properties";
            URL url = getUrlFromPathClass(config, classz);
            if (map == null || !map.containsKey(url)) init(url);
            if (map.containsKey(url)) value = map.get(url).getProperty(key);
        }
        return value;
    }

    /**
     * 通过相对类路径的位置查找对应文件地址
     *
     * @param path
     * @param classz
     * @功能:
     */
    @SuppressWarnings("unused")
    private static URL getUrlFromPathClass(String path, Class<?> classz) {
        return classz.getClassLoader().getResource(path);
    }

    public static String getUrlFromPathClass(String path) {
        return getUrlFromPathClass(path, PropUtil.class).getPath();
    }

    /**
     * 根据文件路径初始化properties属性
     */
    private static synchronized void init(URL url) {
        try {
            String configPath = null;
            //URL url = PropUtil.class.getClassLoader().getResource(config);
            if (url != null) {
                configPath = url.getFile();
                Properties properties = new Properties();
                properties.load(new FileInputStream(configPath));
                if (map.containsKey(url)) map.remove(url);
                map.put(url, properties);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//        System.out.println(PropUtil.getProp("test", "pe", PropUtil.class));
//    }
}
