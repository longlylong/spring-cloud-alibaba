package com.thatday.user.service.user.impl;

import com.thatday.user.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


//    QUser user = QUser.user;
//    QGroupInfo info = QGroupInfo.groupInfo;
//
//    List<HomeListData> results = queryFactory
//            .select(user, info)
//            .from(user, info)
////                .leftJoin(info).on(user.id.eq(info.userId))
//            .where(user.id.eq(info.userId)).fetch().stream().map(t -> {
//                HomeListData indexData = new HomeListData();
//                FileGroup groupInfo = new FileGroup();
//                indexData.setGroup(groupInfo);
//
//                BeanUtils.copyProperties(t.get(info), groupInfo);
//                BeanUtils.copyProperties(t.get(user), indexData);
//
//
//                return indexData;
//            }).collect(Collectors.toList());
}
