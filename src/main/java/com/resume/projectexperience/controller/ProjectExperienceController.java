package com.resume.projectexperience.controller;

import com.resume.projectexperience.entity.ProjectExperienceEntity;
import com.resume.projectexperience.service.ProjectExperienceService;
import com.resume.util.spring.controller.BaseController;
import com.resume.util.spring.service.BaseService;
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
@RequestMapping(value = "/projectexperience")
public class ProjectExperienceController extends BaseController<ProjectExperienceEntity> {
    @Resource
    private ProjectExperienceService projectExperienceService;

    @Override
    public BaseService<ProjectExperienceEntity> getBaseService() {
        return projectExperienceService;
    }
}
