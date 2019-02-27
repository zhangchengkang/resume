/**
 * Copyright © 2017湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @接口方法说明: BaseDao.java
 * @Prject:
 * @Package: com.kingtopware.common.framework.spring.dao
 * @ClassName: BaseDao.java
 * @注意事项:
 * @author: Cui WenKe
 * @date: 2017/3/16 19:34
 * @version: V1.0
 */
package com.resume.util.spring.dao;

import com.resume.util.spring.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @ClassName: BaseDao
 * @注意事项:
 * @author: Cui WenKe
 * @date: 2017-03-16 16:40
 */
public interface BaseDao<T extends BaseEntity> extends PagingAndSortingRepository<T, String>, JpaSpecificationExecutor<T> {
}
