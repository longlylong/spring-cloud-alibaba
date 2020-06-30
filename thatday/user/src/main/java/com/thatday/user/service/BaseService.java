package com.thatday.user.service;

import com.thatday.user.repository.BaseDao;

public interface BaseService <ENTITY, ID, DAO extends BaseDao<ENTITY, ID>> {

    //可自己定义id
    String customDatabaseId();

    ENTITY getOne(ID id);

    DAO getDao();

    void saveOrUpdate(ENTITY entity);
}
