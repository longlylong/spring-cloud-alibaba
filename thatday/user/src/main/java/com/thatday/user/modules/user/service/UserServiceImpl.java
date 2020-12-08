package com.thatday.user.modules.user.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thatday.base.JPAUtil;
import com.thatday.base.service.BaseServiceImpl;
import com.thatday.common.model.PageResult;
import com.thatday.common.utils.IdGen;
import com.thatday.user.modules.user.dao.UserDao;
import com.thatday.user.modules.user.dto.UserDTO;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String, UserDao> implements UserService {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public String customDatabaseId() {
        return "U" + IdGen.getNextTimeCode();
    }

    @Override
    public User loginByPhone(LoginPhoneVo loginPhoneVo) {
        return getLastOneById();
    }

    @Override
    public void addUser(String nickname) {
        User user = new User();
        user.setNickname(nickname);
        saveOrUpdate(user);
    }

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

        Page<User> pageList = getPage(pageRequest, (root, criteriaQuery, builder, predicates) -> {
            predicates.add(builder.equal(root.get("id"), "1"));
            //......
            //.....
        });


        PageResult<UserDTO> pageDTOList = getPageResultToDTO(pageRequest, UserDTO.class, (root, criteriaQuery, builder, predicates) -> {
            predicates.add(builder.equal(root.get("id"), "1"));
            //......
            //.....
        }, (userDTO, user) -> {
            userDTO.setOtherField("some field");
        });
    }
}
