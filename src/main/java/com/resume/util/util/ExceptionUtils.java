/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: ExceptionUtils.java
 * @Prject: kingtopware-common
 * @Package: com.kingtopware.common.util
 * @注意事项: 关于异常的工具类
 * @author: chengjin  
 * @date: 2016年12月1日 下午4:31:12
 * @version: V1.0  
 *  
 */
package com.resume.util.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class ExceptionUtils {
    private ExceptionUtils() {
        throw new UnsupportedOperationException("Not allow construct!");
    }

    /**
     * 将CheckedException转换为UncheckedException.
     */
    public static RuntimeException unchecked(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }

    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起.
     */
    public static boolean isCausedBy(Exception ex, List<Class<? extends Exception>> causeExceptionClasses) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }
}
