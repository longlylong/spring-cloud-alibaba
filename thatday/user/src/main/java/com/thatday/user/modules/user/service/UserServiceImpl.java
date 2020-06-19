package com.thatday.user.modules.user.service;

import com.thatday.user.modules.user.dao.UserDao;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import com.thatday.user.modules.user.vo.LoginWeChatVo;
import com.thatday.user.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseService<UserDao, User> implements UserService {


    @Override
    public User loginByPhone(LoginPhoneVo loginPhoneVo) {
        return null;
    }

    @Override
    public User loginByWeChat(LoginWeChatVo loginWeChatVo) {
        return null;
    }

    @Override
    public String save(String id) {
        User user = new User();
        user.setId(id);

        user.setNickname(System.currentTimeMillis() + "");
        saveOrUpdate(user);

        return user.getId();
    }

}
