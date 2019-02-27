package com.resume.workexperience.controller;

import com.resume.util.spring.controller.BaseController;
import com.resume.util.spring.service.BaseService;
import com.resume.workexperience.entity.WorkExperienceEntity;
import com.resume.workexperience.service.WorkExperienceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: resume
 * @description: 用户信息控制层
 * @author: kangshifu
 * @create: 2019-02-27 22:16
 **/
@RestController
@RequestMapping(value = "/workexperience")
public class WorkExperienceController extends BaseController<WorkExperienceEntity> {
    @Resource
    private WorkExperienceService workExperienceService;

    @Override
    public BaseService<WorkExperienceEntity> getBaseService() {
        return workExperienceService;
    }
}
