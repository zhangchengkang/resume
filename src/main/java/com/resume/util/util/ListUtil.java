/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: KtwListUtil.java
 * @Prject: kingtopware-common
 * @Package: com.kingtopware.common.util
 * @注意事项: 数组扩展处理类
 * @author: chengjin  
 * @date: 2016年12月1日 下午4:31:12
 * @version: V1.0  
 *  
 */
package com.resume.util.util;

import java.util.List;

public class ListUtil {
    private ListUtil() {
        throw new UnsupportedOperationException("Not Allow construct!");
    }

    /**
     * 判断对象为null或者没有元素时返回true
     *
     * @param list
     * @功能:
     */
    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }
}
