package com.resume.vocationalskills.service;

import com.resume.util.spring.dao.BaseDao;
import com.resume.util.spring.service.BaseServiceImpl;
import com.resume.vocationalskills.dao.VocationalSkillsDao;
import com.resume.vocationalskills.entity.VocationalSkillsEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: resume
 * @description: 用户信息服务实现层
 * @author: kangshifu
 * @create: 2019-02-27 22:13
 **/
@Service
public class VocationalSkillsServiceImpl extends BaseServiceImpl<VocationalSkillsEntity> implements VocationalSkillsService {
    @Resource
    private VocationalSkillsDao vocationalSkillsDao;

    @Override
    public BaseDao<VocationalSkillsEntity> getBaseDao() {
        return vocationalSkillsDao;
    }

}
