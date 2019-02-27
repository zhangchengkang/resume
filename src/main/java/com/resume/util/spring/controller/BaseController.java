/**
 * Copyright © 2017湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: BaseController.java
 * @Prject:
 * @Package: com.kingtopware.common.framework.spring.controller
 * @ClassName: BaseController.java
 * @注意事项:
 * @author: Cui WenKe
 * @date: 2018/5/15
 * @version: V1.0
 */
package com.resume.util.spring.controller;

import com.alibaba.fastjson.JSONObject;
import com.resume.util.response.HttpResponse;
import com.resume.util.spring.entity.BaseEntity;
import com.resume.util.util.StringTool;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * BaseEntity管理
 *
 * @ClassName: BaseController
 * @author: Cui WenKe
 * @date: 2018/5/15
 */
public abstract class BaseController<T extends BaseEntity> extends QueryController<T> {
    /**
     * 保存
     *
     * @param entity: 信息
     * @主键: id
     * @功能: 新增或更新
     * @接口方法说明: 根据主键ID更新一条记录、主键为空时新增一条记录
     * @logtypename: 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public HttpResponse<String> sava(@RequestBody T entity) throws Exception {
        HttpResponse<String> checked = checked(entity);
        //数据数据唯一性
        if (200 != checked.getStatusCode()) {
            return checked;
        }
        if (StringTool.empty(entity.getId())) {
            return getBaseService().addEntity(entity);
        } else {
            return getBaseService().updateEntity(entity);
        }
    }

    /**
     * 新增
     *
     * @param entity: 信息
     * @功能: 新增
     * @接口方法说明: 根据请求新增一条记录
     * @logtypename: 新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public HttpResponse<String> add(@RequestBody T entity) throws Exception {
        return sava(entity);
    }

    /**
     * 修改
     *
     * @param entity: 信息
     * @主键: id
     * @功能: 修改
     * @接口方法说明: 根据主键修改一条记录
     * @注意事项: 修改时主键不能为空
     * @logtypename: 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public HttpResponse<String> update(@RequestBody T entity) throws Exception {
        return sava(entity);
    }

    /**
     * 删除
     *
     * @param id: 主键
     * @功能: 根据ID删除一条记录
     * @接口方法说明: 根据ID删除一条记录
     * @logtypename: 删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public HttpResponse<String> delete(@PathVariable String id) throws Exception {
        return getBaseService().delete(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @功能: 根据ID集合删除多条记录
     * @接口方法说明: 根据ID集合删除多条记录
     * @logtypename: 删除
     */
    @RequestMapping(value = "/deletebyids", method = RequestMethod.POST)
    public HttpResponse<String> deleteByIds(@RequestBody List<String> ids) throws Exception {
        return getBaseService().deleteByIds(ids);
    }

    /**
     * 重名验证
     *
     * @param entity: BaseEntity
     * @功能: 校验名称是否重复
     * @接口方法说明: 校验名称是否重复
     */
    @RequestMapping(value = "/checked", method = RequestMethod.POST)
    public HttpResponse<String> validatechecked(@RequestBody JSONObject entity) throws Exception {
        return getBaseService().checked(JSONObject.parseObject(entity.toJSONString(), getBaseService().getJavaType().getClass()));
    }

    public HttpResponse<String> checked(T entity) throws Exception {
        return getBaseService().checked(entity);
    }
//    public HttpResponse<String> validate(BaseEntity entity) {
//        return getBaseService().checked(entity);
//    }
}
