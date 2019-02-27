package com.resume.util.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * 获取CPU核数
 *
 * @author Cui WenKe
 */
public class CpuNum {
    /**
     * 获取cpu核数
     *
     * @return
     */
    public static int getCpuNum() {
        int num = 1;
        // 操作系统
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().startsWith("windows")) {
            num = Runtime.getRuntime().availableProcessors();
        } else {
            //查看逻辑CPU个数
            String cmdCpuCount = " cat /proc/cpuinfo |grep processor|sort -u|wc -l ";
            String cpuCount = execLinuxCmd(cmdCpuCount);
            if (cpuCount != null) {
                String[] arr = cpuCount.split(":");
                if (arr.length > 0) {
                    num = Integer.valueOf(arr[arr.length - 1]);
                }
                if (num <= 0) {
                    num = 1;
                }
            }
        }
        return num;
    }

    public static Object exec(String cmd) {
        try {
            String[] cmdA = {"/bin/sh", "-c", cmd};
            Process process = Runtime.getRuntime().exec(cmdA);
            LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 执行linux命令
     *
     * @param cmd
     * @return
     */
    public static String execLinuxCmd(String cmd) {
        String result = "";
        InputStream in = null;
        try {
            String[] cmdA = {"/bin/sh", "-c", cmd};
            Process pro = Runtime.getRuntime().exec(cmdA);
            pro.waitFor();
            in = pro.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            result = read.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.print(CpuNum.getCpuNum());
    }
}
