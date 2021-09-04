package com.thatday.base.service;

import com.thatday.base.repository.BaseDao;
import com.thatday.base.repository.BaseEntity;

public interface BaseService<ENTITY extends BaseEntity, ID, DAO extends BaseDao<ENTITY, ID>> extends FreeBaseService<ENTITY, ID, DAO> {


}
