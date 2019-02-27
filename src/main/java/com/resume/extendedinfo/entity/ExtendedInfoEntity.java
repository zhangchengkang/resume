package com.resume.extendedinfo.entity;

import com.resume.util.spring.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: resume
 * @description: 扩展信息实体
 * @author: kangshifu
 * @create: 2019-02-27 22:07
 **/
@Data
@Builder
@Entity
@Table(name = "extendedinfo")
public class ExtendedInfoEntity extends BaseEntity {
    /**
     * 信息
     */
    @Column(name = "info")
    private String info;

    /**
     * 用户ID
     */
    @Column(name = "userId")
    private String userId;

    @Tolerate
    public ExtendedInfoEntity() {
    }

    @Override
    public String[] modeCode() {
        return new String[]{"5", "拓展信息"};
    }
}
