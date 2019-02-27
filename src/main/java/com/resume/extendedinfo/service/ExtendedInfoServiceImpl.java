package com.resume.extendedinfo.service;

import com.resume.extendedinfo.dao.ExtendedInfoDao;
import com.resume.extendedinfo.entity.ExtendedInfoEntity;
import com.resume.util.spring.dao.BaseDao;
import com.resume.util.spring.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: resume
 * @description: 扩展信息服务实现层
 * @author: kangshifu
 * @create: 2019-02-27 22:13
 **/
@Service
public class ExtendedInfoServiceImpl extends BaseServiceImpl<ExtendedInfoEntity> implements ExtendedInfoService {
    @Resource
    private ExtendedInfoDao extendedInfoDao;

    @Override
    public BaseDao<ExtendedInfoEntity> getBaseDao() {
        return extendedInfoDao;
    }

}
