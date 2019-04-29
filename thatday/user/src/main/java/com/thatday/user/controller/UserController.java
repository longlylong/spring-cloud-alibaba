package com.thatday.user.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thatday.common.model.ResponseModel;
import com.thatday.user.entity.db.GroupInfo;
import com.thatday.user.entity.db.QGroupInfo;
import com.thatday.user.entity.db.QUser;
import com.thatday.user.entity.db.User;
import com.thatday.user.entity.dto.IndexData;
import com.thatday.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JPAQueryFactory queryFactory;

    @GetMapping(value = "/getGroupInfo")
    public ResponseModel getGroupInfo(Long userId) {
        QUser user = QUser.user;
        QGroupInfo info = QGroupInfo.groupInfo;

        List<IndexData> results = queryFactory
                .select(user, info)
                .from(user, info)
//                .leftJoin(info).on(user.id.eq(info.userId))
                .where(user.id.eq(info.userId)).fetch().stream().map(t -> {
                    IndexData indexData = new IndexData();
                    GroupInfo groupInfo = new GroupInfo();
                    indexData.setGroup(groupInfo);

                    BeanUtils.copyProperties(t.get(info), groupInfo);
                    BeanUtils.copyProperties(t.get(user), indexData);


                    return indexData;
                }).collect(Collectors.toList());
        return ResponseModel.buildSuccess(results);
    }

    @GetMapping(value = "/loginByPhone")
    public ResponseModel loginByPhone(String phone, String psw) {
        List<User> users = userRepository.findUserByPhoneAndPassword(phone, psw);
        if (CollectionUtils.isEmpty(users)) {
            return ResponseModel.build(100, "login failed");
        }

        return ResponseModel.buildSuccess(users.get(0));
    }

    @GetMapping(value = "/getUserById")
    public ResponseModel getUserById(Long userId) {
        return ResponseModel.buildSuccess(userRepository.findByIdEquals(userId));
    }

    @GetMapping(value = "/loginByWeChat")
    public ResponseModel loginByWeChat(String openId) {
        List<User> users = userRepository.findUserByWeChatOpenId(openId);
        if (CollectionUtils.isEmpty(users)) {
            return ResponseModel.build(100, "login failed");
        }
        User user = users.get(0);
        user.setLoginTime(new Timestamp(new Date().getTime()));
        userRepository.save(user);
        return ResponseModel.buildSuccess(user);
    }
}
