package com.resume.vocationalskills.controller;

import com.resume.util.spring.controller.BaseController;
import com.resume.util.spring.service.BaseService;
import com.resume.vocationalskills.entity.VocationalSkillsEntity;
import com.resume.vocationalskills.service.VocationalSkillsService;
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
@RequestMapping(value = "/vocationalskills")
public class VocationalSkillsController extends BaseController<VocationalSkillsEntity> {
    @Resource
    private VocationalSkillsService vocationalSkillsService;

    @Override
    public BaseService<VocationalSkillsEntity> getBaseService() {
        return vocationalSkillsService;
    }
}
