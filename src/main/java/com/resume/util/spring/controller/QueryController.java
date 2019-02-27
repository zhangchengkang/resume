/**
 * Copyright © 2017湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: OperationController
 * @Prject: server-project
 * @Package: com.kingtopware.common.framework.spring.controller
 * @ClassName: OperationController
 * @注意事项: 操作接口控制层
 * @author: Cui WenKe
 * @date: 2017/7/18
 * @version: V1.0
 */
package com.resume.util.spring.controller;

import com.alibaba.fastjson.JSONObject;
import com.resume.util.response.*;
import com.resume.util.spring.entity.BaseEntity;
import com.resume.util.spring.service.BaseService;
import com.resume.util.util.StringTool;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * BaseEntity查询
 *
 * @ClassName: QueryController
 * @author: Cui WenKe
 * @date: 2017/7/18
 */
@SuppressWarnings("unchecked")
public abstract class QueryController<T extends BaseEntity> {
    public abstract BaseService<T> getBaseService();

    /**
     * 根据主键查询详情
     *
     * @param id: 主键
     * @功能: 根据主键查询详情
     * @接口方法说明: 根据主键查询详情
     */
    @RequestMapping(value = "/findbyid/{id}")
    public HttpResponse<?> findById(@PathVariable String id) {
        try {
            return new HttpResponse<T>(getBaseService().findById(id));
        } catch (Exception e) {
            e.printStackTrace();
            //服务器异常
            return new HttpResponse(500);
        }
    }

    /**
     * 查询全部信息
     *
     * @param objCondition: 查询条件
     * @paramAsNull: 参数可以为空
     * @功能: 查询全部信息
     * @接口方法说明: 根据条件查询全部信息
     * @注意事项: 参数为空时查询所有信息
     */
    @RequestMapping(value = "/findall")
    public HttpResponseList<?> findAll(@RequestBody JSONObject objCondition) {
        return getBaseService().findAll(objCondition);
    }

    /**
     * 查询分页信息
     *
     * @param pageInfo: 查询条件
     * @功能: 查询分页信息
     * @接口方法说明: 查询分页信息
     */
    @RequestMapping(value = "/findpagelist")
    public HttpResponsePageList<?> findPageList(@RequestBody PageInfo pageInfo) {
        return getBaseService().findPageList(pageInfo);
    }

    /**
     * 查询列表信息
     *
     * @功能: 查询列表信息
     * @接口方法说明: 根据条件查询列表信息
     * @注意事项: 参数为空时查询所有信息
     */
    @RequestMapping(value = "/gridlist")
    public JSONObject gridList(HttpServletRequest request) {
        JSONObject param = getJSONObject(request);
        PageList<T> getData = getBaseService().findPageList(getPageInfo(param)).getData();
        param.put("dataSource", getData.getDataSource());
        param.put("totalCount", getData.getPageInfo().getTotalCount());
        return param;
    }

    public PageInfo getPageInfo(JSONObject param) {
        PageInfo pageInfo = new PageInfo(param.getInteger("page"), param.getInteger("limit"));
        pageInfo.setObjCondition(param);
        String orderBy = param.getString("orderby");
        if (StringTool.noEmpty(orderBy)) {
            pageInfo.setOrderby(orderBy);
        }
        return pageInfo;
    }

    /**
     * 查询上下级结构信息
     *
     * @功能: 查询上下级结构信息
     * @接口方法说明: 根据条件查询上下级结构信息
     * @注意事项: 参数为空时查询所有信息
     */
    @RequestMapping(value = "/treeload")
    public JSONObject treeLoad(HttpServletRequest request) {
        String parentid = StringTool.toEmptySafe(request.getParameter("node"));
        String check = request.getParameter("check");
        Iterable<BaseEntity> findAll = getBaseService().findAll(null, request.getParameter("sort"));
        JSONObject result = new JSONObject();
        result.put("children", getChildren(check, parentid, findAll));
        return result;
    }

    public JSONObject getJSONObject(HttpServletRequest request) {
        JSONObject objCondition = new JSONObject();
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            objCondition.put(paraName, request.getParameter(paraName));
        }
        return objCondition;
    }

    public List<JSONObject> getChildren(String check, String parentid, Iterable<BaseEntity> findAll) {
        List<JSONObject> objectList = new ArrayList<>();
        for (BaseEntity entity : findAll) {
            JSONObject item = JSONObject.parseObject(JSONObject.toJSONString(entity));
            if (StringTool.equalsVal(item.getString(entity.parentid()), parentid)) {
                Boolean leaf = true;
                for (BaseEntity children : findAll) {
                    JSONObject node = JSONObject.parseObject(JSONObject.toJSONString(children));
                    if (StringTool.equalsVal(node.getString(children.parentid()), entity.getId())) {
                        leaf = false;
                    }
                }
                String name = item.getString(entity.text());
                item.put("text", name);
                item.put("leaf", leaf);
                if (StringTool.equals(check, "false")) {
                    item.put("checked", false);
                }
                objectList.add(item);
            }
        }
        return objectList;
    }
}