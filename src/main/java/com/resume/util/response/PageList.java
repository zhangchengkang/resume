/**
 *   
 * Copyright © 2016湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: PageList.java
 * @Prject: kingtopware-common
 * @Package: com.kingtopware.common.response
 * @注意事项: 通用HTTP返回前端分页组装对象
 * @author: chengjin  
 * @date: 2016年12月1日 下午4:31:12
 * @version: V1.0  
 *  
 */
package com.resume.util.response;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "PageList")
public class PageList<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private PageInfo pageInfo;
    private List<T> dataSource;

    public PageList() {
    }

    public PageList(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public PageList(List<T> dataSource) {
        this.dataSource = dataSource;
    }

    public PageList(PageInfo pageInfo, List<T> dataSource) {
        this.pageInfo = pageInfo;
        this.dataSource = dataSource;
    }

    public List<T> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<T> dataSource) {
        this.dataSource = dataSource;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
