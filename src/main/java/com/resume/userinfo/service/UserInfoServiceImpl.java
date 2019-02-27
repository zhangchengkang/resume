package com.resume.userinfo.service;

import com.resume.userinfo.dao.UserInfoDao;
import com.resume.userinfo.entity.UserInfoEntity;
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
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoEntity> implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public BaseDao<UserInfoEntity> getBaseDao() {
        return userInfoDao;
    }

}
