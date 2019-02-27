package com.resume.util.util;

/**
 * 后去当类所在的jar名称和jar路径
 *
 * @author Cui WenKe
 */
public class JarPath {
    private String jarName;
    private String jarPath;

    @SuppressWarnings("rawtypes")
    public JarPath(Class clazz) {
        String path = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        try {
            path = java.net.URLDecoder.decode(path, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        java.io.File jarFile = new java.io.File(path);
        this.jarName = jarFile.getName();
        java.io.File parent = jarFile.getParentFile();
        if (parent != null) {
            this.jarPath = parent.getAbsolutePath();
        }
    }

    /**
     * 获取Class类所在Jar包的名称
     *
     * @return Jar包名 (例如：C:\temp\demo.jar 则返回 demo.jar )
     */
    public String getJarName() {
        try {
            return java.net.URLDecoder.decode(this.jarName, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得Class类所在的Jar包路径
     *
     * @return 返回一个路径 (例如：C:\temp\demo.jar 则返回 C:\temp )
     */
    public String getJarPath() {
        try {
            return java.net.URLDecoder.decode(this.jarPath, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static void main(String[] args) throws Exception {
//        JarPath ju = new JarPath(BeanContext.class);
//        System.out.println("Jar name: " + ju.getJarName());
//        System.out.println("Jar path: " + ju.getJarPath());
//    }
}
