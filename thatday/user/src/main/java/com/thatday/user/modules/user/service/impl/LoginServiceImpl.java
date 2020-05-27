package com.thatday.user.modules.user.service.impl;

import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import com.thatday.user.modules.user.vo.LoginWeChatVo;
import com.thatday.user.modules.user.dao.LoginRepository;
import com.thatday.user.modules.user.service.LoginService;
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
