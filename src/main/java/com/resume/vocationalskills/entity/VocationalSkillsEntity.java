package com.resume.vocationalskills.entity;

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
@Table(name = "vocationalSkills")
public class VocationalSkillsEntity extends BaseEntity {

    /**
     * 技能
     */
    @Column(name = "skill")
    private String skill;

    /**
     * 用户ID
     */
    @Column(name = "userId")
    private String userId;

    @Tolerate
    public VocationalSkillsEntity() {
    }

    @Override
    public String[] modeCode() {
        return new String[]{"4", "职业技能"};
    }
}
