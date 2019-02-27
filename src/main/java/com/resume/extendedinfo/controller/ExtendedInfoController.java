package com.resume.extendedinfo.controller;

import com.resume.extendedinfo.entity.ExtendedInfoEntity;
import com.resume.extendedinfo.service.ExtendedInfoService;
import com.resume.util.spring.controller.BaseController;
import com.resume.util.spring.service.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: resume
 * @description: 扩展信息控制层
 * @author: kangshifu
 * @create: 2019-02-27 22:16
 **/
@RestController
@RequestMapping(value = "/extendinfo")
public class ExtendedInfoController extends BaseController<ExtendedInfoEntity> {
    @Resource
    private ExtendedInfoService extendedInfoService;

    @Override
    public BaseService<ExtendedInfoEntity> getBaseService() {
        return extendedInfoService;
    }
}
