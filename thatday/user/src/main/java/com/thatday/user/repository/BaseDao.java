package com.thatday.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseDao<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    T findFirstByIdEquals(Long id);

//    可变多条件查询
//    Specification<ProjectInfo> specification = new Specification() {
//        @Override
//        public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
//
//            List<Predicate> predicates = new ArrayList<Predicate>();
//
//            predicates.add(criteriaBuilder.equal(root.get("isDelete"), false));
//
//            if (StringUtils.isNotBlank(vo.getParkName())) {
//                predicates.add(criteriaBuilder.like(root.get("parkName"), "%" + vo.getParkName() + "%"));
//            }
//
//             if (StringUtils.isNotBlank(vo.getSearchText())) {
//                   predicates.add(builder.or(
//                  builder.or(builder.like(root.get("provinceName"), "%" + vo.getSearchText() + "%")),
//                  builder.or(builder.like(root.get("cityName"), "%" + vo.getSearchText() + "%"))
//                  ));
//          }
//            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//        }
//    };

//    连表
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