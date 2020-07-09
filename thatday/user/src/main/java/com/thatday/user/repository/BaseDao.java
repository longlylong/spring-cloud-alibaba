package com.thatday.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseDao<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    T findFirstByIdEquals(ID id);

    //连表查下面的数据
//    @OneToMany(targetEntity = 目标表对象.class, fetch = FetchType.EAGER)
//    @JoinTable(name = "目标表", joinColumns = {@JoinColumn(name = "目标表外键")},
//            inverseJoinColumns = {@JoinColumn(name = "本表主键")})
//    private List<目标表对象> objList;

    //自定义连表
//    private JPAQueryFactory queryFactory;
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
