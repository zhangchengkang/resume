package com.resume.userinfo.service;

import com.resume.userinfo.entity.UserInfoEntity;
import com.resume.util.response.HttpResponse;
import com.resume.util.spring.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserInfoService extends BaseService<UserInfoEntity> {
    HttpResponse<UserInfoEntity> login(UserInfoEntity loginInfo, HttpServletRequest request, HttpServletResponse response);
}
