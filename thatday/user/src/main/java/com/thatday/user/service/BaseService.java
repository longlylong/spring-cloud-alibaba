package com.thatday.user.service;

import com.thatday.common.exception.GlobalException;
import com.thatday.common.utils.IdGen;
import com.thatday.user.repository.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

public class BaseService<DAO extends BaseDao, ENTITY> {

    @Autowired
    protected DAO dao;

    public String saveOrUpdate(ENTITY entity) {
        String id = getId(entity);
        if (StringUtils.isEmpty(id)) {
            id = IdGen.uuid();
            setId(id, entity);
            dao.save(entity);
        } else {
            dao.saveAndFlush(entity);
        }
        return id;
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
