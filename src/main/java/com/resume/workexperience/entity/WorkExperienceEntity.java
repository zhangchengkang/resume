package com.resume.workexperience.entity;

import com.resume.util.spring.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: resume
 * @description: 用户实体
 * @author: kangshifu
 * @create: 2019-02-27 22:07
 **/
@Data
@Builder
@Entity
@Table(name = "workExperience")
public class WorkExperienceEntity extends BaseEntity {
    /**
     * 公司名
     */
    @Column(name = "companyName")
    private String companyName;

    /**
     * 职位
     */
    @Column(name = "position")
    private String position;

    /**
     * 任职时间段
     */
    @Column(name = "time")
    private String time;

    /**
     * 职责描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 用户Id
     */
    @Column(name = "userId")
    private String userId;

    //默认唯一名称字段
    public String name() {
        return "companyName";
    }

    @Tolerate
    public WorkExperienceEntity() {
    }

    @Override
    public String[] modeCode() {
        return new String[]{"2", "工作经历"};
    }
}
