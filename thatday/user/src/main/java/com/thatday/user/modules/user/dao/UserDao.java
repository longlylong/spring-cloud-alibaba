package com.thatday.user.modules.user.dao;

import com.thatday.base.repository.BaseDao;
import com.thatday.user.modules.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseDao<User, String> {


}
