package com.thatday.user.service;

import com.thatday.common.exception.GlobalException;
import com.thatday.common.model.PageInfoVo;
import com.thatday.common.model.PageResult;
import com.thatday.common.token.UserInfo;
import com.thatday.common.utils.TemplateCodeUtil;
import com.thatday.user.repository.BaseDao;
import com.thatday.user.repository.BaseEntity;
import com.thatday.user.repository.JPAUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Set;

public abstract class BaseServiceImpl<ENTITY extends BaseEntity, ID, DAO extends BaseDao<ENTITY, ID>> implements BaseService<ENTITY, ID, DAO> {

    @Autowired
    protected DAO dao;

    @Override
    public DAO getDao() {
        return dao;
    }

    @Override
    public ENTITY getOne(ID id) {
        return dao.findFirstByIdEquals(id);
    }

    @Override
    public ENTITY getOneAndCheckNull(ID id, String prefix) {
        ENTITY entity = getOne(id);
        checkNull(entity, prefix + "错误或不存在");
        return entity;
    }

    @Override
    public ENTITY getLastOneById() {
        PageRequest pageRequest = JPAUtil.prIdDesc(0, 1);
        Page<ENTITY> page = getPageList(pageRequest, null);
        if (page.getContent().isEmpty()) {
            return null;
        }
        return page.getContent().get(0);
    }

    @Override
    public void saveOrUpdate(ENTITY entity) {
        ID id = getId(entity);
        if (id == null || StringUtils.isEmpty(id.toString())) {
            setId(customDatabaseId(), entity);
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            dao.save(entity);
        } else {
            entity.setUpdateTime(new Date());
            dao.saveAndFlush(entity);
        }
    }

    @Override
    public <S extends ENTITY> List<S> saveAll(Iterable<S> list) {
        return dao.saveAll(list);
    }

    @Override
    public List<ENTITY> getAllList(JPAUtil.SpecificationListener otherConditionListener) {
        Specification<ENTITY> specification = JPAUtil.makeSpecification((root, criteriaQuery, builder, predicates) -> {
            if (otherConditionListener != null) {
                otherConditionListener.addSpecification(root, criteriaQuery, builder, predicates);
            }
        });
        return dao.findAll(specification);
    }

    @Override
    public <TARGET> List<TARGET> getAllDTOList(Class<TARGET> targetClass, JPAUtil.SpecificationListener otherConditionListener, TemplateCodeUtil.OnTransListener<TARGET, ENTITY> transDTOListener) {
        return TemplateCodeUtil.transTo(getAllList(otherConditionListener), targetClass, transDTOListener);
    }

    @Override
    public Page<ENTITY> getPageList(PageRequest pageRequest, JPAUtil.SpecificationListener otherConditionListener) {
        Specification<ENTITY> specification = JPAUtil.makeSpecification((root, criteriaQuery, builder, predicates) -> {
            if (otherConditionListener != null) {
                otherConditionListener.addSpecification(root, criteriaQuery, builder, predicates);
            }
        });
        return dao.findAll(specification, pageRequest);
    }

    @Override
    public PageResult<ENTITY> getPageResultList(PageRequest pageRequest, JPAUtil.SpecificationListener otherConditionListener) {
        Page<ENTITY> page = getPageList(pageRequest, otherConditionListener);
        return JPAUtil.setPageResult(pageRequest.getPageNumber(), page);
    }

    @Override
    public <TARGET> PageResult<TARGET> getPageResultDTOList(PageRequest pageRequest, Class<TARGET> targetClass,
                                                            JPAUtil.SpecificationListener otherConditionListener,
                                                            TemplateCodeUtil.OnTransListener<TARGET, ENTITY> transDTOListener) {
        Page<ENTITY> pageList = getPageList(pageRequest, otherConditionListener);
        return JPAUtil.setPageResult(pageRequest.getPageNumber(), pageList, targetClass, transDTOListener);
    }

    @Override
    public <TARGET> PageResult<TARGET> getPageResultStickDTOList(PageInfoVo vo, @NotNull Set<ID> stickIds, Class<TARGET> targetClass,
                                                                 JPAUtil.SpecificationListener otherConditionListener,
                                                                 TemplateCodeUtil.OnTransListener<TARGET, ENTITY> stickDTOListener,
                                                                 TemplateCodeUtil.OnTransListener<TARGET, ENTITY> otherDTOListener) {
        Integer pageSize = vo.getPageSize();

        Page<ENTITY> page = getStickList(vo, stickIds, new JPAUtil.StickPageRequest(vo), false, otherConditionListener);
        PageResult<TARGET> dtoPageResult = JPAUtil.setPageResult(vo.getCurPage(), page, targetClass, stickDTOListener);

        if (page.getContent().size() < vo.getPageSize()) {
            vo.setPageSize(vo.getPageSize() - page.getContent().size());
            Page<ENTITY> otherPage = getStickList(vo, stickIds, new JPAUtil.StickPageRequest(vo), true, otherConditionListener);
            PageResult<TARGET> otherDto = JPAUtil.setPageResult(vo.getCurPage(), otherPage, targetClass, otherDTOListener);

            dtoPageResult.setTotalCount(dtoPageResult.getTotalCount() + otherDto.getTotalCount());
            dtoPageResult.getDataList().addAll(otherDto.getDataList());
            dtoPageResult.setTotalPage((int) (dtoPageResult.getTotalCount() / pageSize) + 1);
        }
        return dtoPageResult;
    }

    private Page<ENTITY> getStickList(PageInfoVo vo, Set<ID> stickIds, JPAUtil.StickPageRequest pageRequest, boolean loadOther, JPAUtil.SpecificationListener otherConditionListener) {
        stickIds.add((ID) "-1");

        if (loadOther) {
            int stickSize = stickIds.size() - 1;
            if (vo.getCurPage() >= stickSize / vo.getPageSize() + 1) {
                pageRequest.setStickOffset(stickSize);
            }
        }

        return getPageList(pageRequest, (root, criteriaQuery, builder, predicates) -> {
            if (loadOther) {
                predicates.add(builder.not(root.get("id").in(stickIds)));
            } else {
                predicates.add(builder.and(root.get("id").in(stickIds)));
            }
            if (otherConditionListener != null) {
                otherConditionListener.addSpecification(root, criteriaQuery, builder, predicates);
            }
        });
    }

    protected void checkOwner(UserInfo userInfo, String ownerId) {
        if (!ownerId.equals(userInfo.getUserId())) {
            throw GlobalException.createParam("权限不足");
        }
    }

    protected void checkNull(Object object, String errorMsg) {
        if (object == null) {
            throw GlobalException.createParam(errorMsg);
        }
    }


    private void setId(ID id, ENTITY entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw GlobalException.createError("no id field");
        }
    }

    private ID getId(ENTITY entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return (ID) idField.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw GlobalException.createError("no id field");
        }
    }
}
