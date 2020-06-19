package com.thatday.user.modules.user.service;

import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import com.thatday.user.modules.user.vo.LoginWeChatVo;

public interface UserService {

    User loginByPhone(LoginPhoneVo loginPhoneVo);

    User loginByWeChat(LoginWeChatVo loginWeChatVo);

    String save(String id);
}
