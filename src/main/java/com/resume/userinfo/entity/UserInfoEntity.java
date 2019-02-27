package com.resume.userinfo.entity;

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
@Table(name = "userinfo")
public class UserInfoEntity extends BaseEntity {
    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 年龄
     */
    @Column(name = "age")
    private String age;

    /**
     * 电话
     */
    @Column(name = "tel")
    private String tel;

    /**
     * 籍贯
     */
    @Column(name = "homeAddress")
    private String homeAddress;

    /**
     * qq
     */
    @Column(name = "qq")
    private String qq;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 毕业院校
     */
    @Column(name = "university")
    private String university;

    /**
     * 专业
     */
    @Column(name = "major")
    private String major;

    /**
     * 博客地址
     */
    @Column(name = "blogAdderss")
    private String blogAdderss;

    /**
     * 简历地址
     */
    @Column(name = "resumeAdderss")
    private String resumeAdderss;

    /**
     * 毕业时间
     */
    @Column(name = "graduationTime")
    private String graduationTime;

    /**
     * 求职意向
     */
    @Column(name = "jobIntention")
    private String jobIntention;

    /**
     * 期望薪资
     */
    @Column(name = "salaryExpectation")
    private String salaryExpectation;

    /**
     * 头像地址
     */
    @Column(name = "headPath")
    private String headPath;

    @Tolerate
    public UserInfoEntity() {
    }


    @Override
    public String[] modeCode() {
        return new String[]{"1", "用户信息"};
    }
}
