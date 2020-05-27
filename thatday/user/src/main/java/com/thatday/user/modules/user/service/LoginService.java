package com.thatday.user.modules.user.service;

import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import com.thatday.user.modules.user.vo.LoginWeChatVo;

public interface LoginService {

    User loginByPhone(LoginPhoneVo loginPhoneVo);

    User loginByWeChat(LoginWeChatVo loginWeChatVo);
}
