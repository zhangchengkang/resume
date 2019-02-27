/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: PageInfo.java
 * @Prject: kingtopware-common
 * @Package: com.kingtopware.common.response
 * @注意事项: 通用HTTP返回前端分页数码对象
 * @author: chengjin  
 * @date: 2016年12月1日 下午4:31:12
 * @version: V1.0  
 *  
 */
package com.resume.util.response;

import com.alibaba.fastjson.JSONObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "PageInfo")
public class PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 当前页
     *
     * @isNotNull: true
     */
    private int pageIndex;
    /**
     * 每页总数
     *
     * @isNotNull: true
     */
    private int pageSize;
    private int totalCount;
    private int pageCount;
    private String orderby;
    private JSONObject objCondition;

    public PageInfo() {
    }

    public PageInfo(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public PageInfo(int pageIndex, int pageSize, int totalCount) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public PageInfo(int pageIndex, int pageSize, int totalCount, int pageCount) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.pageCount = pageCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        int s = totalCount / pageSize;
        int y = totalCount % pageSize;
        this.pageCount = y > 0 ? s + 1 : s;
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public JSONObject getObjCondition() {
        return objCondition;
    }

    public void setObjCondition(JSONObject objCondition) {
        this.objCondition = objCondition;
    }
}
