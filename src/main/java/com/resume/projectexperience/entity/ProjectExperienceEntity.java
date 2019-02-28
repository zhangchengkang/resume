package com.resume.projectexperience.entity;

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
@Table(name = "projectExperience")
public class ProjectExperienceEntity extends BaseEntity {
    /**
     * 项目名
     */
    @Column(name = "name")
    private String name;
    /**
     * 项目时间段
     */
    @Column(name = "time")
    private String time;

    /**
     * 项目描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 用户ID
     */
    @Column(name = "userId")
    private String userId;


    @Tolerate
    public ProjectExperienceEntity() {
    }

    @Override
    public String[] modeCode() {
        return new String[]{"3", "项目经验"};
    }
}
