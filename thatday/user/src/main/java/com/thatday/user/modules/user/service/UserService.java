package com.thatday.user.modules.user.service;

import com.thatday.user.modules.user.dao.UserDao;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import com.thatday.user.modules.user.vo.LoginWeChatVo;
import com.thatday.user.service.BaseService;
import org.springframework.stereotype.Service;

public interface UserService extends BaseService<User, String, UserDao> {

    User loginByPhone(LoginPhoneVo loginPhoneVo);

    User loginByWeChat(LoginWeChatVo loginWeChatVo);


}
