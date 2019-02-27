/**
 * Copyright © 2017湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: BaseEntity.java
 * @Prject: server-project
 * @Package: com.kingtopware.common.framework.spring.entity
 * @ClassName: BaseEntity.java
 * @注意事项: 数据库表实体基础类(主键默认string类型)
 * @author: ZhuLing
 * @date: 2017/5/9
 * @version: V1.0
 */
package com.resume.util.spring.entity;

import com.resume.util.util.ObjectUtil;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuidGen")
    @GenericGenerator(name = "uuidGen", strategy = "uuid")
    @Column(name = "\"ID\"", length = 32)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //模糊查询的字段名
    public List<String> likeArr() {
        return new ArrayList<>();
    }

    //集合查询的字段名
    public List<String> inArr() {
        return new ArrayList<>();
    }

    //非查询的字段名称
    public List<String> notArr() {
        return new ArrayList<>();
    }

    //默认排序字段
    public String sort() {
        return "createTime";
    }

    //默认唯一名称字段
    public String name() {
        return "name";
    }

    //默认唯一名称字段
    public String getIdentifier() {
        return ObjectUtil.getFieldValue(this, name());
    }

    //默认文本字段
    public String text() {
        return name();
    }

    //父节点编号
    public String parentid() {
        return "parentid";
    }

    //模块编码
    public abstract String[] modeCode();
}
