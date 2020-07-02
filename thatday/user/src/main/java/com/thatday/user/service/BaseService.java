package com.thatday.user.service;

import com.thatday.user.repository.BaseDao;

import java.util.List;

public interface BaseService<ENTITY, ID, DAO extends BaseDao<ENTITY, ID>> {

    DAO getDao();

    //可自己定义id
    String customDatabaseId();

    ENTITY getOne(ID id);

    void saveOrUpdate(ENTITY entity);

    <S extends ENTITY> List<S> saveAll(Iterable<S> list);


}
