package com.resume.userinfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.resume.extendedinfo.service.ExtendedInfoService;
import com.resume.projectexperience.service.ProjectExperienceService;
import com.resume.userinfo.entity.UserInfoEntity;
import com.resume.userinfo.service.UserInfoService;
import com.resume.util.response.HttpResponse;
import com.resume.util.response.HttpResponseList;
import com.resume.util.spring.controller.BaseController;
import com.resume.util.spring.service.BaseService;
import com.resume.vocationalskills.service.VocationalSkillsService;
import com.resume.workexperience.service.WorkExperienceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * @接口方法说明: login
     * @接口方法说明: 用户登录
     * @param: loginInfo
     * @功能:: HttpResponse<UserLoginDto>
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HttpResponse<UserInfoEntity> login(@RequestBody UserInfoEntity loginInfo, HttpServletRequest request, HttpServletResponse response) {
        return userInfoService.login(loginInfo, request, response);
    }

    @RequestMapping(value = "/findone", method = RequestMethod.POST)
    public HttpResponseList findOne(@RequestBody UserInfoEntity loginInfo) {
        List list = new ArrayList();
        list.add(findById(loginInfo.getId()).getData());
        return new HttpResponseList(list);
    }
}
