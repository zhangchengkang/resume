package com.resume.workexperience.service;

import com.resume.util.spring.dao.BaseDao;
import com.resume.util.spring.service.BaseServiceImpl;
import com.resume.workexperience.dao.WorkExperienceDao;
import com.resume.workexperience.entity.WorkExperienceEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: resume
 * @description: 用户信息服务实现层
 * @author: kangshifu
 * @create: 2019-02-27 22:13
 **/
@Service
public class WorkExperienceServiceImpl extends BaseServiceImpl<WorkExperienceEntity> implements WorkExperienceService {
    @Resource
    private WorkExperienceDao workExperienceDao;

    @Override
    public BaseDao<WorkExperienceEntity> getBaseDao() {
        return workExperienceDao;
    }

}
