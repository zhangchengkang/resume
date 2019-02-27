/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 * <p>
 *  @接口方法说明: HttpResponse.java
 *  @Prject: kingtopware-common
 *  @Package: com.kingtopware.common.response
 *  @注意事项: 通用HTTP返回前端对象
 *  @author: chengjin  
 *  @date: 2016年12月1日 下午4:31:12
 *  @version: V1.0  
 *  
 */
package com.resume.util.response;

import java.io.Serializable;

public class HttpResponse<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 业务状态码
     */
    private int statusCode;
    /**
     * 业务返回描述
     */
    private String message;
    private Boolean success = true;
    /**
     * 返回数据
     */
    private T data;

    public HttpResponse() {
        this(200);
    }

    public HttpResponse(int code) {
        this(code, "");
    }

    public HttpResponse(Boolean success, String message) {
        this.statusCode = 200;
        this.success = success;
        this.message = message;
    }

    public HttpResponse(int code, String message) {
        this.statusCode = code;
        this.message = message;
    }

    public HttpResponse(T data) {
        this(200, "", data);
    }

    public HttpResponse(int code, String message, T data) {
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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
