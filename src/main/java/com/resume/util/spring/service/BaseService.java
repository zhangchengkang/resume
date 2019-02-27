/**
 * Copyright © 2017湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: BaseService.java
 * @Prject:
 * @Package: com.kingtopware.common.framework.spring.service
 * @ClassName: BaseService.java
 * @注意事项:
 * @author: Cui WenKe
 * @date: 2017/3/16 19:29
 * @version: V1.0
 */
package com.resume.util.spring.service;

import com.alibaba.fastjson.JSONObject;
import com.resume.util.response.HttpResponse;
import com.resume.util.response.HttpResponseList;
import com.resume.util.response.HttpResponsePageList;
import com.resume.util.response.PageInfo;
import com.resume.util.spring.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName: BaseCommonService
 * @注意事项: 服务层基类接口
 * @author: Cui WenKe
 * @date: 2017-03-16 16:20
 */
public interface BaseService<T extends BaseEntity> {
    /**
     * 新增信息
     *
     * @param entity: 信息
     * @功能:
     */
    T add(T entity);

    Iterable<T> add(List<T> entitys);

    HttpResponse<String> addEntity(T entity);

    /**
     * 更新信息
     *
     * @param entity: 信息
     * @功能:
     */
    T update(T entity);

    Iterable<T> update(List<T> entitys);

    HttpResponse<String> updateEntity(T entity);

    /**
     * 删除信息
     *
     * @param id: 主键
     * @功能:
     */
    HttpResponse<String> delete(String id);

    HttpResponse<String> delete(Iterable<T> entitys);

    HttpResponse<String> deleteByIds(List<String> ids);

    /**
     * 根据主键查询信息
     *
     * @param id: 主键
     * @功能:
     */
    T findById(String id);

    /**
     * 查询全部信息
     *
     * @功能:
     */
    Iterable findAll();

    Iterable findAll(T entity);

    Iterable findAll(List<String> ids);

    Iterable findAll(JSONObject spec, String orderBy);

    Page findAll(JSONObject spec, Pageable pageable);

    HttpResponseList findAll(JSONObject objCondition);

    /**
     * 查询分页信息
     *
     * @功能:
     */
    HttpResponsePageList<T> findPageList(PageInfo pageInfo);

    /**
     * 根据名称查询
     *
     * @param name
     * @功能:
     */
    List<T> findByName(String name);

    /**
     * 新增或修改时校验用户名是否重复
     *
     * @param entity:对象
     * @功能:
     */
    HttpResponse<String> checked(BaseEntity entity);

    HttpResponse responseErrorCode(String code, String message);

    T getJavaType();
}
