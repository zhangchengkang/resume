package com.resume.projectexperience.service;

import com.resume.projectexperience.dao.ProjectExperienceDao;
import com.resume.projectexperience.entity.ProjectExperienceEntity;
import com.resume.util.spring.dao.BaseDao;
import com.resume.util.spring.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: resume
 * @description: 用户信息服务实现层
 * @author: kangshifu
 * @create: 2019-02-27 22:13
 **/
@Service
public class ProjectExperienceServiceImpl extends BaseServiceImpl<ProjectExperienceEntity> implements ProjectExperienceService {
    @Resource
    private ProjectExperienceDao projectExperienceDao;

    @Override
    public BaseDao<ProjectExperienceEntity> getBaseDao() {
        return null;
    }

}
