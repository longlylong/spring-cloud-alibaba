package com.thatday.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseDao<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    T findFirstById(ID id);

    //自定义sql
//    @Query(value = "select stu.* from rel_student_class sc " +
//            "join t_student_user stu on sc.student_user_id = stu.student_user_id  " +
//            "where sc.class_id = ?1 order by stu.student_user_id asc limit ?2 offset ?3 ", nativeQuery = true)
//    List<StudentUser> findByClassId(String classId, Integer pageSize, Integer curPage);

    //中间表连表 使用数据库的字段名
//    @OneToMany
//    @ManyToMany
//    @JoinTable(name = "中间表表名(需要本表和目标表的主键作为联合主键)",
//            joinColumns = {@JoinColumn(name = "本表关联的主键", referencedColumnName = "本表关联的主键")},
//            inverseJoinColumns = {@JoinColumn(name = "目标表关联的主键", referencedColumnName = "目标表关联的主键")})
//    private Set<SchoolClass> schoolClassList;

    //本表连下级表查下面的数据 使用Entity的字段名
//    @OneToMany(targetEntity = 目标表对象.class, fetch = FetchType.EAGER)
//    @JoinTable(name = "目标表", joinColumns = {@JoinColumn(name = "目标表外键")},
//            inverseJoinColumns = {@JoinColumn(name = "本表主键")})
//    private List<目标表对象> objList;

    //代码的自定义连表
//    private JPAQueryFactory queryFactory;
//    QUser user = QUser.user;
//    QGroupInfo info = QGroupInfo.groupInfo;
//
//    List<HomeListData> results = queryFactory
//            .select(user, info)
//            .from(user, info)
//            .leftJoin(info).on(user.id.eq(info.userId))
//            .where(user.id.eq(info.userId)).fetch().stream().map(t -> {
//                HomeListData indexData = new HomeListData();
//                FileGroup groupInfo = new FileGroup();
//                indexData.setGroup(groupInfo);
//
//                BeanUtils.copyProperties(t.get(info), groupInfo);
//                BeanUtils.copyProperties(t.get(user), indexData);
//
//                return indexData;
//            }).collect(Collectors.toList());
}
