package com.resume.userinfo.service;

import com.resume.userinfo.dao.UserInfoDao;
import com.resume.userinfo.entity.UserInfoEntity;
import com.resume.util.response.HttpResponse;
import com.resume.util.spring.dao.BaseDao;
import com.resume.util.spring.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: resume
 * @description: 用户信息服务实现层
 * @author: kangshifu
 * @create: 2019-02-27 22:13
 **/
@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoEntity> implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public BaseDao<UserInfoEntity> getBaseDao() {
        return userInfoDao;
    }

    @Override
    public HttpResponse<UserInfoEntity> login(UserInfoEntity loginInfo, HttpServletRequest request, HttpServletResponse response) {
        UserInfoEntity userEntity = userInfoDao.findByUsername(loginInfo.getUsername());
        //用户为空表示 当前用户名的用户不存在
        if (userEntity == null) {
            return new HttpResponse<>(30002, "用户名不存在");
        }
        if (!userEntity.getPassword().equals(loginInfo.getPassword())) {
            return new HttpResponse<>(30001, "密码错误");
        }
        response.setHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin,Access-Control-Allow-Credentials,TOKEN");
        return new HttpResponse<>(200,"登陆成功",userEntity);
    }
}
