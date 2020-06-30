package com.thatday.user.service;

import com.thatday.common.exception.GlobalException;
import com.thatday.common.utils.IdGen;
import com.thatday.user.repository.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

public class BaseServiceImpl<ENTITY, ID, DAO extends BaseDao<ENTITY, ID>>
        implements BaseService<ENTITY, ID, DAO> {

    @Autowired
    protected DAO dao;

    //可自己定义id
    @Override
    public String customDatabaseId() {
        return IdGen.uuid();
    }

    @Override
    public ENTITY getOne(ID id) {
        return dao.findFirstByIdEquals(id);
    }

    @Override
    public DAO getDao() {
        return dao;
    }

    @Override
    public void saveOrUpdate(ENTITY entity) {
        String id = getId(entity);
        if (StringUtils.isEmpty(id)) {
            id = customDatabaseId();
            setId(id, entity);
            dao.save(entity);
        } else {
            dao.saveAndFlush(entity);
        }
    }

    private void setId(String id, ENTITY entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw GlobalException.createError("no id field");
        }
    }

    private String getId(ENTITY entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return (String) idField.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw GlobalException.createError("no id field");
        }
    }
}
