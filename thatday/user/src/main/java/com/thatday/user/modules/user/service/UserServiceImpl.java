package com.thatday.user.modules.user.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thatday.common.model.PageResult;
import com.thatday.user.modules.user.dao.UserDao;
import com.thatday.user.modules.user.dto.UserDTO;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import com.thatday.user.modules.user.vo.LoginWeChatVo;
import com.thatday.user.repository.JPAUtil;
import com.thatday.user.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String, UserDao> implements UserService {

    public User loginByPhone(LoginPhoneVo loginPhoneVo) {
        return getLastOneById();
    }

    public User loginByWeChat(LoginWeChatVo loginWeChatVo) {
        return null;
    }

    @Override
    public String customDatabaseId() {
        return null;
    }

    @Autowired
    private JPAQueryFactory queryFactory;

    public void dslTest() {
//        QUser qUser = QUser.user;
//        QDir qDir = QDir.dir;
//
//        List<HomeDTO> results = queryFactory
//                .select(qUser, qDir)
//                .from(qUser, qDir)
//                .where(qUser.id.eq(qDir.userId)).fetch().stream().map(t -> {
//                    HomeDTO dto = new HomeDTO();
//
//                    User user = TemplateCodeUtil.transTo(t.get(qUser), User.class);
//                    Dir dir = TemplateCodeUtil.transTo(t.get(qDir), Dir.class);
//                    return dto;
//                }).collect(Collectors.toList());
    }

    public void test() {
        PageRequest pageRequest = JPAUtil.prCreateTimeDesc(0, 10);

        Page<User> pageList = getPageList(pageRequest, (root, criteriaQuery, builder, predicates) -> {
            predicates.add(builder.equal(root.get("id"), "1"));
            //......
            //.....
        });


        PageResult<UserDTO> pageDTOList = getPageResultDTOList(pageRequest, UserDTO.class, (root, criteriaQuery, builder, predicates) -> {
            predicates.add(builder.equal(root.get("id"), "1"));
            //......
            //.....
        }, (userDTO, user) -> {
            userDTO.setOtherField("some field");
        });
    }
}
