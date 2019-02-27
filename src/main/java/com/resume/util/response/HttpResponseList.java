/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: HttpResponseList.java
 * @Prject: kingtopware-common
 * @Package: com.kingtopware.common.response
 * @注意事项: 通用HTTP返回前端对象<list>数组模式
 * @author: chengjin
 * @date: 2016年12月1日 下午4:31:12
 * @version: V1.0  
 *  
 */
package com.resume.util.response;

import java.io.Serializable;
import java.util.List;

public class HttpResponseList<T extends Serializable> implements Serializable {
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
     * 返回列表数据
     */
    private List<T> data;

    public HttpResponseList() {
        this(200);
    }

    public HttpResponseList(int code) {
        this(code, "");
    }

    public HttpResponseList(int code, String message) {
        this.statusCode = code;
        this.message = message;
    }

    public HttpResponseList(List<T> data) {
        this(200, "", data);
    }

    public HttpResponseList(int code, String message, List<T> data) {
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
