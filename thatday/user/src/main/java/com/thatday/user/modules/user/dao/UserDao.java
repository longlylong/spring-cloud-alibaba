package com.thatday.user.modules.user.dao;

import com.thatday.user.modules.user.entity.User;
import com.thatday.user.repository.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseDao<User, String> {


}
