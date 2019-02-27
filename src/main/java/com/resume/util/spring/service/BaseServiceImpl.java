/**
 * Copyright © 2017湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: BaseServiceImpl.java
 * @Prject:
 * @Package: com.kingtopware.common.framework.spring.service
 * @ClassName: BaseServiceImpl.java
 * @注意事项:
 * @author: Cui WenKe
 * @date: 2017/3/16 19:32
 * @version: V1.0
 */
package com.resume.util.spring.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.resume.util.response.*;
import com.resume.util.spring.dao.BaseDao;
import com.resume.util.spring.entity.BaseEntity;
import com.resume.util.util.DateUtil;
import com.resume.util.util.ObjectUtil;
import com.resume.util.util.StringTool;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: BaseCommonServiceImpl
 * @注意事项: 服务层基类实现类
 * @author: Cui WenKe
 * @date: 2017-03-16 16:23
 */
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {
    public abstract BaseDao<T> getBaseDao();

    /**
     * 新增信息
     *
     * @param entity: 信息
     * @功能:
     */
    public HttpResponse<String> addEntity(T entity) {
        return new HttpResponse<>(add(entity).getId());
    }

    public T add(T entity) {
        try {
            return save(entity);
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Iterable<T> add(List<T> entitys) {
        try {
            return save(entitys);
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新信息
     *
     * @param entity: 信息
     * @功能:
     */
    public HttpResponse<String> updateEntity(T entity) {
        return new HttpResponse<>(update(entity).getId());
    }

    public T update(T entity) {
        if (null == entity || StringTool.empty(entity.getId())) {
            return entity;
        }
        Optional<T> update = getBaseDao().findById(entity.getId());
        if (!update.isPresent()) {
            return null;
        }
        JSONObject entityOld = JSONObject.parseObject(JSON.toJSONString(update));
        JSONObject entityNew = JSONObject.parseObject(JSON.toJSONString(entity));
        entityOld.putAll(entityNew);
        T instance = JSONObject.parseObject(entityOld.toJSONString(), (Class<T>) entity.getClass());
        try {
            return save(instance);
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Iterable<T> update(List<T> entitys) {
        try {
            return save(entitys);
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除信息
     *
     * @param id: 主键
     * @功能:
     */
    public HttpResponse<String> delete(String id) {
        Optional<T> findOne = getBaseDao().findById(id);
        findOne.ifPresent(this::delete);
        return new HttpResponse<>();
    }

    /**
     * 通过集合删除信息
     *
     * @param ids
     */
    public HttpResponse<String> deleteByIds(List<String> ids) {
        return delete(getBaseDao().findAllById(ids));
    }

    public HttpResponse<String> delete(Iterable<T> entitys) {
        if (null != entitys) {
            for (T entity : entitys) {
                try {
                    delete(entity);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }
        return new HttpResponse<>();
    }

    /**
     * 根据主键查询信息
     *
     * @param id: 主键
     * @功能:
     */
    public T findById(String id) {
        return getBaseDao().findById(id).orElse(null);
    }

    /**
     * 查询全部信息
     *
     * @功能:
     */
    public Iterable findAll() {
        return getBaseDao().findAll();
    }

    public Iterable findAll(T entity) {
        return findAll(JSONObject.parseObject(JSONObject.toJSONString(entity)), entity.sort());
    }

    public Iterable findAll(List<String> ids) {
        return getBaseDao().findAllById(ids);
    }

    public Iterable findAll(JSONObject spec, String orderBy) {
        return getBaseDao().findAll(getSpecification(spec), getSort(orderBy));
    }

    public Page findAll(JSONObject spec, Pageable pageable) {
        return getBaseDao().findAll(getSpecification(spec), pageable);
    }

    public HttpResponseList findAll(JSONObject objCondition) {
        String orderBy = null;
        if (null != objCondition) {
            orderBy = objCondition.getString("orderby");
        }
        return new HttpResponseList((List) findAll(objCondition, orderBy));
    }

    /**
     * 查询分页信息
     *
     * @功能:
     */
    public HttpResponsePageList findPageList(PageInfo pageInfo) {
        int page = pageInfo.getPageIndex() > 0 ? pageInfo.getPageIndex() - 1 : 0;
        PageRequest pageRequest = new PageRequest(page, pageInfo.getPageSize(), getSort(pageInfo.getOrderby()));
        Page pageResult = findAll(pageInfo.getObjCondition(), pageRequest);
        pageInfo.setTotalCount(Long.valueOf(pageResult.getTotalElements()).intValue());
        HttpResponsePageList responsePageList = new HttpResponsePageList();
        responsePageList.setData(new PageList(pageInfo, pageResult.getContent()));
        return responsePageList;
    }

    /**
     * 根据名称查询
     *
     * @param name
     * @功能:
     */
    public List<T> findByName(String name) {
        return getBaseDao().findAll(new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.and(builder.equal(root.get(getJavaType().name()), name));
            }
        });
    }

    /**
     * 新增或修改时校验用户名是否重复
     *
     * @param entity:对象
     * @功能:
     */
    public HttpResponse<String> checked(BaseEntity entity) {
        String name = entity.getIdentifier();
        if (StringTool.noEmpty(entity.getId())) {
            //判断数据是否存在
            if (null == findById(entity.getId())) {
                //ID不存在：1 编辑修改	2 该条件下无数据	1操作失败
                return responseErrorCode("121", "ID不存在");
            }
        } else {
            //判断名称是否为空
            if (StringTool.empty(name)) {
                //名称不能为空：0 新增  	0 参数丢失	1操作失败
                return responseErrorCode("001", "名称不能为空");
            }
        }
        //判断是否重名
        if (StringTool.noEmpty(name)) {
            List<T> findByName = getBaseDao().findAll(new Specification<T>() {
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                    List<Predicate> andList = new ArrayList<>();
                    if (StringTool.noEmpty(entity.getId())) {
                        andList.add(builder.notEqual(root.get("id"), entity.getId()));
                    }
                    String parentid = ObjectUtil.getFieldValue(entity, entity.parentid());
                    if (StringTool.noEmpty(parentid)) {
                        List<String> attributeNames = getAttributeNames(getJavaType());
                        //判断是否查询今天的信息
                        if (attributeNames.contains(entity.parentid())) {
                            andList.add(builder.equal(root.get(entity.parentid()), parentid));
                        }
                    }
                    andList.add(builder.equal(root.get(entity.name()), name));
                    return builder.and(andList.toArray(new Predicate[andList.size()]));
                }
            });
            //名称已存在：1 编辑修改	1 参数有误	1操作失败
            if (null != findByName && !findByName.isEmpty()) {
                return responseErrorCode("111", "名称已存在");
            }
        }
        return new HttpResponse<>();
    }

    public HttpResponse responseErrorCode(String code, String message) {
        String[] model = getJavaType().modeCode();
        if (null == model && 2 != model.length) {
            return new HttpResponse(500, "实体类描述信息缺失！");
        }
        return new HttpResponse(Integer.valueOf(model[0] + code), model[1] + message);
    }

    /**
     * 获取泛型实体类
     *
     * @功能:
     */
    public T getJavaType() {
        ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type type = superclass.getActualTypeArguments()[0];
        return JSONObject.parseObject("{}", type);
    }

    /**
     * 初始化默认查询条件
     *
     * @param root         :基类
     * @param builder      :查询条件
     * @param objCondition :查询参数对象
     * @功能:
     */
    public List<Predicate> initDefaultPredicate(Root<T> root, CriteriaBuilder builder, JSONObject objCondition) {
        if (null == root || null == builder || null == objCondition) {
            throw new NullPointerException();
        }
        List<Predicate> all = new ArrayList<>();
        try {
            List<String> attributeNames = getAttributeNames(getJavaType());
            //判断是否查询今天的信息
            if (attributeNames.contains("date")) {
                String today = objCondition.getString("today");
                if (StringTool.noEmpty(today)) {
                    SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_STANDARD);
                    Timestamp startDate = new Timestamp(format.parse(today + DateUtil.START_TIME).getTime());
                    Timestamp endDate = new Timestamp(format.parse(today + DateUtil.END_TIME).getTime());
                    all.add(builder.between(root.get("date"), startDate, endDate));
                }
            }
            if (attributeNames.contains("createTime")) {
                //获取传入的日期
                Long start = 0L;
                Long end = 0L;
                if (objCondition.containsKey("startTime") && StringTool.noEmpty(objCondition.getString("startTime"))) {//传入的日期为字符串 格式为yyyy-MM-dd
                    start = DateUtil.string2Date(objCondition.getString("startTime"), "yyyy-MM-dd").getTime();
                } else {
                    if (objCondition.containsKey("start")) {//传入的日期为时间戳
                        start = objCondition.getLong("start");
                    }
                }
                if (objCondition.containsKey("endTime") && StringTool.noEmpty(objCondition.getString("endTime"))) {
                    end = DateUtil.string2Date(objCondition.getString("endTime"), "yyyy-MM-dd").getTime();
                } else {
                    if (objCondition.containsKey("end")) {
                        end = objCondition.getLong("end");
                    }
                }
                if ((null != start && start != 0) || (null != end && end != 0)) {
                    SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_DATE);
                    //查询时间段信息
                    //没有传入开始日期的时候，设置开始日期为"2000-01-01"
                    Timestamp startDate = new Timestamp(format.parse(DateUtil.START_DATE).getTime());
                    //没有传入结束日期的时候，设置结束日期为当前时间
                    Timestamp endDate = new Timestamp(format.parse(DateUtil.END_DATE).getTime());
                    if (null != start && start != 0) {
                        startDate = new Timestamp(start);
                    }
                    if (null != end && end != 0) {
                        //把日期往后增加一天.整数往后推,负数往前移动
                        endDate = new Timestamp(DateUtil.getAfterDate(new Date(end)).getTime());
                    }
                    all.add(builder.between(root.get("createTime"), startDate, endDate));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return all;
    }

    /**
     * 根据查询参数组合查询条件
     *
     * @param jsonObject :查询参数对象
     * @功能:
     */
    public Specification getSpecification(JSONObject jsonObject) {
        return getSpecification(jsonObject, getJavaType());
    }

    public Specification getSpecification(JSONObject condition, BaseEntity instance) {
        if (null == condition) {
            return null;
        } else {
            List<String> attributes = getAttributeNames(getJavaType());
            return new Specification<T>() {
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                    //初始化默认查询条件
                    List<Predicate> orList = new ArrayList<>();
                    List<Predicate> andList = initDefaultPredicate(root, builder, condition);
                    Set<Map.Entry<String, Object>> entries = condition.entrySet();
                    for (Map.Entry<String, Object> entry : entries) {
                        //判断如果查询参数键在实体类中并且值不为空时根据查询参数组合查询条件
                        String key = entry.getKey();
                        Object val = entry.getValue();
                        if (attributes.contains(key) && !instance.notArr().contains(key)) {
                            if (null != val && StringTool.noEmpty(val.toString())) {
                                Path path = root.get(key);
                                if (instance.likeArr().contains(key)) {
                                    andList.add(builder.like(builder.upper(path), "%" + val.toString().toUpperCase()
                                            + "%"));
                                } else if (instance.inArr().contains(key)) {
                                    if (val instanceof List) {
                                        andList.add(path.in(val));
                                    } else {
                                        andList.add(path.in(Arrays.asList(val.toString().split(","))));
                                    }
                                } else {
                                    andList.add(builder.equal(path, ObjectUtil.typeConvert(getJavaType(), key, val
                                            .toString())));
                                }
                            }
                        } else if (key.indexOf("or_") >= 0) {
                            if (attributes.contains(key.replace("or_", "")))
                                orList.add(builder.equal(root.get(key.replace("or_", "")), ObjectUtil.typeConvert
                                        (getJavaType(), key, val.toString())));
                        }
                    }
                    //获取查询参数对象
                    if (attributes.contains("deleState") && !condition.containsKey("deleState")) {
                        andList.add(builder.equal(root.get("deleState"), 0));
                    }
                    Predicate predicate = builder.and(andList.toArray(new Predicate[andList.size()]));
                    if (orList.isEmpty()) {
                        return predicate;
                    } else {
                        return builder.or(predicate, builder.and(orList.toArray(new Predicate[orList.size()])));
                    }
                }
            };
        }
    }

    /**
     * 获取实体类所有属性名称
     *
     * @功能:
     */
    public List<String> getAttributeNames(BaseEntity instance) {
        List<String> list = new ArrayList<>();
        Field[] declaredFields = instance.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            list.add(field.getName());
        }
        return list;
    }

    private T invoke(T entity) {
        if (null == entity) {
            return entity;
        }
        List<String> attributeNames = getAttributeNames(entity);
        if (attributeNames.contains("createTime") && StringTool.empty(entity.getId())) {
            ObjectUtil.invokeSetGetMethod(entity, "createTime", new Timestamp(System.currentTimeMillis()));
        }
        if (attributeNames.contains("updateTime")) {
            ObjectUtil.invokeSetGetMethod(entity, "updateTime", new Timestamp(System.currentTimeMillis()));
        }
        return entity;
    }

    public Sort getSort(String orderby) {
        T instance = getJavaType();
        Sort sort = null;
        if (StringTool.empty(orderby) && null != instance) {
            if (getAttributeNames(instance).contains(instance.sort())) {
                sort = new Sort(Sort.Direction.DESC, instance.sort());
            }
        } else {
            int index = orderby.lastIndexOf("_");
            String propertie = index >= 0 ? orderby.substring(0, index) : orderby;
            if (getAttributeNames(instance).contains(propertie)) {
                String direction = StringTool.getLastSuffix(orderby, "_");
                if (StringTool.equalsVal("desc", direction.trim().toLowerCase())) {
                    sort = new Sort(Sort.Direction.DESC, propertie);
                } else {
                    sort = new Sort(propertie);
                }
            }
        }
        return sort;
    }

    private T save(T entity) throws ServiceException {
        return getBaseDao().save(invoke(entity));
    }

    private Iterable<T> save(List<T> entitys) throws ServiceException {
        for (T entity : entitys) {
            invoke(entity);
        }
        return getBaseDao().saveAll(entitys);
    }

    private void delete(T entity) throws ServiceException {
        getBaseDao().delete(entity);
    }
}
