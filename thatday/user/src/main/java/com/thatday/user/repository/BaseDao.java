package com.thatday.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseDao<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    T findFirstByIdEquals(Long id);

//    可变多条件查询
//        JPAUtil.makeSpecification(new JPAUtil.SpecificationListener() {
//            @Override
//            public void addSpecification(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder builder, List<Predicate> predicates) {
//                predicates.add(criteriaBuilder.equal(root.get("isDelete"), false));
//
//                predicates.add(builder.and(root.get("projectId").in(provinceList)));
//
//                if (StringUtils.isNotBlank(vo.getParkName())) {
//                    predicates.add(criteriaBuilder.like(root.get("parkName"), "%" + vo.getParkName() + "%"));
//                }
//
//                if (StringUtils.isNotBlank(vo.getSearchText())) {
//                    predicates.add(builder.or(
//                            builder.like(root.get("provinceName"), "%" + vo.getSearchText() + "%"),
//                            builder.like(root.get("cityName"), "%" + vo.getSearchText() + "%")
//                    ));
//            }
//        });

    //连表查下面的数据
//    @OneToMany(targetEntity = 目标表对象.class, fetch = FetchType.EAGER)
//    @JoinTable(name = "目标表", joinColumns = {@JoinColumn(name = "目标表外键")},
//            inverseJoinColumns = {@JoinColumn(name = "本表主键")})
//    private List<目标表对象> objList;

//    自定义连表
//    private JPAQueryFactory queryFactory;
//    Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
//    Pageable pageable = PageRequest.of(vo.getCurPage(), vo.getPageSize(), sort);
//
//    Page<ProjectInfo> dataPage = projectInfoRepository.findAll(specification, pageable);
//
//    PageInfo<ProjectSimpleDTO> pageInfo = new PageInfo<>();
//    pageInfo.setCurPage(vo.getCurPage());
//    pageInfo.setTotalCount(dataPage.getTotalElements());
//    pageInfo.setTotalPage(dataPage.getTotalPages());

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