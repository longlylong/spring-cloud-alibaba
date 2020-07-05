package com.thatday.user.service;

import com.thatday.user.repository.BaseDao;

import java.util.List;

public interface BaseService<ENTITY, ID, DAO extends BaseDao<ENTITY, ID>> {

    //可自己定义id
    ID customDatabaseId();

    DAO getDao();

    ENTITY getOne(ID id);

    ENTITY getOneAndCheckNull(ID id, String msg);

    void saveOrUpdate(ENTITY entity);

    <S extends ENTITY> List<S> saveAll(Iterable<S> list);


}
