package com.thatday.user.modules.user.dao;

import com.thatday.user.modules.user.entity.User;
import com.thatday.user.repository.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepository extends BaseDao<User, Long> {

    List<User> findUserByPhoneAndPassword(String phone, String psw);

    List<User> findUserByWeChatOpenId(String openId);


}