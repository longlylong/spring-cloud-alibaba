package com.thatday.user.service;

import com.thatday.common.exception.GlobalException;
import com.thatday.common.token.UserInfo;
import com.thatday.user.repository.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.List;

public abstract class BaseServiceImpl<ENTITY, ID, DAO extends BaseDao<ENTITY, ID>> implements BaseService<ENTITY, ID, DAO> {

    @Autowired
    protected DAO dao;

    @Override
    public DAO getDao() {
        return dao;
    }

    @Override
    public ENTITY getOne(ID id) {
        return dao.findFirstByIdEquals(id);
    }

    @Override
    public void saveOrUpdate(ENTITY entity) {
        ID id = getId(entity);
        if (id == null || StringUtils.isEmpty(id.toString())) {
            id = customDatabaseId();
            setId(id, entity);
            dao.save(entity);
        } else {
            dao.saveAndFlush(entity);
        }
    }

    @Override
    public <S extends ENTITY> List<S> saveAll(Iterable<S> list) {
        return saveAll(list);
    }

    protected void checkOwner(UserInfo userInfo, String ownerId) {
        if (!userInfo.getUserId().equals(ownerId)) {
            throw GlobalException.createParam("权限不足");
        }
    }

    protected void checkNull(Object object, String errorMsg) {
        if (object == null) {
            throw GlobalException.createParam(errorMsg);
        }
    }

    private void setId(ID id, ENTITY entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw GlobalException.createError("no id field");
        }
    }

    private ID getId(ENTITY entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return (ID) idField.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw GlobalException.createError("no id field");
        }
    }
}
