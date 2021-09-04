package com.thatday.base.service;

import com.thatday.base.repository.BaseDao;
import com.thatday.base.repository.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public abstract class BaseServiceImpl<ENTITY extends BaseEntity, ID, DAO extends BaseDao<ENTITY, ID>> extends FreeBaseServiceImpl<ENTITY, ID, DAO> {

    @Override
    public void saveOrUpdate(ENTITY entity) {
        ID id = getId(entity);
        if (id == null || StringUtils.isEmpty(id.toString())) {
            setId(customDatabaseId(), entity);
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            getDao().save(entity);
        } else {
            entity.setUpdateTime(new Date());
            getDao().saveAndFlush(entity);
        }
    }
}
