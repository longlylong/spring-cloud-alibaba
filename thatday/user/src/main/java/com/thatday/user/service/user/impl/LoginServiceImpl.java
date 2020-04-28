package com.thatday.user.service.user.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thatday.user.entity.db.User;
import com.thatday.user.entity.vo.LoginPhoneVo;
import com.thatday.user.entity.vo.LoginWeChatVo;
import com.thatday.user.repository.LoginRepository;
import com.thatday.user.service.user.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private LoginRepository loginRepository;

    public LoginServiceImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public User loginByPhone(LoginPhoneVo loginPhoneVo) {
        return null;
    }

    @Override
    public User loginByWeChat(LoginWeChatVo loginWeChatVo) {
        return null;
    }

}
