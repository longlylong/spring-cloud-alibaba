package com.thatday.user.modules.user.dao;

import com.thatday.user.modules.user.entity.Dir;
import com.thatday.user.repository.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface DirDao extends BaseDao<Dir, String> {

}
