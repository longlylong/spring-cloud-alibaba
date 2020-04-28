package com.thatday.user.repository;

import com.thatday.user.entity.db.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepository extends BaseDao<User, Long> {

    List<User> findUserByPhoneAndPassword(String phone, String psw);

    List<User> findUserByWeChatOpenId(String openId);


}