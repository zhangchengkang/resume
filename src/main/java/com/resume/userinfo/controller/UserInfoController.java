package com.resume.userinfo.controller;

import com.resume.userinfo.entity.UserInfoEntity;
import com.resume.userinfo.service.UserInfoService;
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
@RequestMapping(value = "/userinfo")
public class UserInfoController extends BaseController<UserInfoEntity> {
    @Resource
    private UserInfoService userInfoService;

    @Override
    public BaseService<UserInfoEntity> getBaseService() {
        return userInfoService;
    }
}
