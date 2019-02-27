/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: HttpResponsePageList.java
 * @Prject: kingtopware-common
 * @Package: com.kingtopware.common.response
 * @注意事项: 通用HTTP返回前端带分页对象<list>数组模式
 * @author: chengjin  
 * @date: 2016年12月1日 下午4:31:12
 * @version: V1.0  
 *  
 */
package com.resume.util.response;

import java.io.Serializable;

public class HttpResponsePageList<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 业务状态码
     */
    private int statusCode;
    /**
     * 业务返回描述
     */
    private String message;
    /**
     * 返回带分页信息的数据
     */
    private PageList<T> data;

    public HttpResponsePageList() {
        this(200);
    }

    public HttpResponsePageList(int code) {
        this(code, "");
    }

    public HttpResponsePageList(int code, String message) {
        this.statusCode = code;
        this.message = message;
    }

    public HttpResponsePageList(PageList<T> data) {
        this(200, "", data);
    }

    public HttpResponsePageList(int code, String message, PageList<T> data) {
        this.statusCode = code;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PageList<T> getData() {
        return data;
    }

    public void setData(PageList<T> pl) {
        this.data = pl;
    }
}
