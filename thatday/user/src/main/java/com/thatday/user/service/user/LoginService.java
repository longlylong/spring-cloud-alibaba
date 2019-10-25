package com.thatday.user.service.user;

import com.thatday.user.entity.db.User;
import com.thatday.user.entity.vo.LoginPhoneVo;
import com.thatday.user.entity.vo.LoginWeChatVo;

public interface LoginService {

    User loginByPhone(LoginPhoneVo loginPhoneVo);

    User loginByWeChat(LoginWeChatVo loginWeChatVo);
}
