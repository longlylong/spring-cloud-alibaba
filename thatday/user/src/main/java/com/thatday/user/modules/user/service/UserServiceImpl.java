package com.thatday.user.modules.user.service;

import com.thatday.common.model.PageResult;
import com.thatday.user.modules.user.dao.UserDao;
import com.thatday.user.modules.user.dto.UserDTO;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import com.thatday.user.modules.user.vo.LoginWeChatVo;
import com.thatday.user.repository.JPAUtil;
import com.thatday.user.service.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String, UserDao> implements UserService {

    public User loginByPhone(LoginPhoneVo loginPhoneVo) {
        return null;
    }

    public User loginByWeChat(LoginWeChatVo loginWeChatVo) {
        return null;
    }


    @Override
    public String customDatabaseId() {
        return null;
    }

    public void test() {
        PageRequest pageRequest = JPAUtil.updateTimeDescPage(0, 10);

        Page<User> pageList = getPageList(pageRequest, (root, criteriaQuery, builder, predicates) -> {
            predicates.add(builder.equal(root.get("id"), "1"));
            //......
            //.....
        });


        PageResult<UserDTO> pageDTOList = getPageDTOList(pageRequest, UserDTO.class, (userDTO, user) -> {

            userDTO.setOtherField("some field");

        }, (root, criteriaQuery, builder, predicates) -> {

            predicates.add(builder.equal(root.get("id"), "1"));
            //......
            //.....
        });
    }
}
