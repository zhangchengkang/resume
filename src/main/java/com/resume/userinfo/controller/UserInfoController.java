package com.resume.userinfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.resume.extendedinfo.service.ExtendedInfoService;
import com.resume.projectexperience.service.ProjectExperienceService;
import com.resume.userinfo.entity.UserInfoEntity;
import com.resume.userinfo.service.UserInfoService;
import com.resume.util.response.HttpResponseList;
import com.resume.util.spring.controller.BaseController;
import com.resume.util.spring.service.BaseService;
import com.resume.vocationalskills.service.VocationalSkillsService;
import com.resume.workexperience.entity.WorkExperienceEntity;
import com.resume.workexperience.service.WorkExperienceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: resume
 * @description: 用户信息控制层
 * @author: kangshifu
 * @create: 2019-02-27 22:16
 **/
@RestController
@RequestMapping(value = "/userinfo")
public class UserInfoController extends BaseController<UserInfoEntity> {
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private ExtendedInfoService extendedInfoService;
    @Resource
    private ProjectExperienceService projectExperienceService;
    @Resource
    private VocationalSkillsService vocationalSkillsService;
    @Resource
    private WorkExperienceService workExperienceService;

    @Override
    public BaseService<UserInfoEntity> getBaseService() {
        return userInfoService;
    }

    /**
     * 查询单个
     */
    @RequestMapping(value = "/findone/{id}", method = RequestMethod.GET)
    public JSONObject findOne(@PathVariable String id) {
        JSONObject requestJson = new JSONObject();
        requestJson.put("userId", id);
        JSONObject responseJson = new JSONObject();
        responseJson.put("userInfo", userInfoService.findById(id));
        responseJson.put("extendedInfo", extendedInfoService.findAll(requestJson).getData());
        responseJson.put("projectExperience", projectExperienceService.findAll(requestJson).getData());
        responseJson.put("vocationalSkills", vocationalSkillsService.findAll(requestJson).getData());
        responseJson.put("workExperience", workExperienceService.findAll(requestJson).getData());
        return responseJson;
    }
}
