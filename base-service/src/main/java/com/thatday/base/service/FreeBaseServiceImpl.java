package com.thatday.base.service;

import com.thatday.base.JPAUtil;
import com.thatday.base.SpecificationListener;
import com.thatday.base.repository.BaseDao;
import com.thatday.common.exception.GlobalException;
import com.thatday.common.model.PageInfoVo;
import com.thatday.common.model.PageResult;
import com.thatday.common.token.UserInfo;
import com.thatday.common.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class FreeBaseServiceImpl<ENTITY, ID, DAO extends BaseDao<ENTITY, ID>> implements FreeBaseService<ENTITY, ID, DAO> {

    @Autowired
    protected DAO dao;

    @Override
    public ID customDatabaseId() {
        return null;
    }

    @Override
    public DAO getDao() {
        return dao;
    }

    @Override
    public ENTITY getOne(ID id) {
        return dao.findFirstById(id);
    }

    @Override
    public ENTITY getOneAndCheckNull(ID id, String prefix) {
        ENTITY entity = getOne(id);
        checkNull(entity, prefix + "错误或不存在");
        return entity;
    }

    @Override
    public ENTITY getLastOneById() {
        return getLastOneById(null);
    }

    @Override
    public ENTITY getLastOneById(SpecificationListener specificationListener) {
        PageRequest pageRequest = JPAUtil.prIdDesc(0, 1);
        Page<ENTITY> page = getPage(pageRequest, specificationListener);
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
            dao.save(entity);
        } else {
            dao.saveAndFlush(entity);
        }
    }

    @Override
    public <S extends ENTITY> List<S> saveAll(Iterable<S> list) {
        return dao.saveAll(list);
    }

    @Override
    public long countAll(SpecificationListener specificationListener) {
        Specification<ENTITY> specification = JPAUtil.makeSpecification((root, criteriaQuery, builder, predicates) -> {
            if (specificationListener != null) {
                specificationListener.addSpecification(root, criteriaQuery, builder, predicates);
            }
        });
        return dao.count(specification);
    }

    @Override
    public List<ENTITY> getAll() {
        return getAll(null);
    }

    @Override
    public List<ENTITY> getAll(SpecificationListener otherConditionListener) {
        Specification<ENTITY> specification = JPAUtil.makeSpecification((root, criteriaQuery, builder, predicates) -> {
            if (otherConditionListener != null) {
                otherConditionListener.addSpecification(root, criteriaQuery, builder, predicates);
            }
        });
        return dao.findAll(specification);
    }

    @Override
    public <TARGET> List<TARGET> getAllToDTO(Class<TARGET> targetClass, SpecificationListener otherConditionListener, BiConsumer<TARGET, ENTITY> transDTOListener) {
        return BeanUtil.transTo(getAll(otherConditionListener), targetClass, transDTOListener);
    }

    @Override
    public Page<ENTITY> getPage(PageRequest pageRequest, SpecificationListener otherConditionListener) {
        Specification<ENTITY> specification = JPAUtil.makeSpecification((root, criteriaQuery, builder, predicates) -> {
            if (otherConditionListener != null) {
                otherConditionListener.addSpecification(root, criteriaQuery, builder, predicates);
            }
        });
        return dao.findAll(specification, pageRequest);
    }

    @Override
    public PageResult<ENTITY> getPageResult(PageRequest pageRequest, SpecificationListener otherConditionListener) {
        Page<ENTITY> page = getPage(pageRequest, otherConditionListener);
        return JPAUtil.setPageResult(pageRequest.getPageNumber(), page);
    }

    @Override
    public <TARGET> PageResult<TARGET> getPageResultToDTO(PageRequest pageRequest, Class<TARGET> targetClass,
                                                          SpecificationListener otherConditionListener,
                                                          BiConsumer<TARGET, ENTITY> transDTOListener) {
        Page<ENTITY> pageList = getPage(pageRequest, otherConditionListener);
        return JPAUtil.setPageResult(pageRequest.getPageNumber(), pageList, targetClass, transDTOListener);
    }

    @Override
    public <TARGET> PageResult<TARGET> getStickPageResultToDTO(PageInfoVo vo, @NotNull Set<ID> stickIds, Class<TARGET> targetClass,
                                                               SpecificationListener otherConditionListener,
                                                               BiConsumer<TARGET, ENTITY> stickDTOListener,
                                                               BiConsumer<TARGET, ENTITY> otherDTOListener) {
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

    private Page<ENTITY> getStickList(PageInfoVo vo, Set<ID> stickIds, JPAUtil.StickPageRequest pageRequest, boolean loadOther, SpecificationListener otherConditionListener) {
        stickIds.add((ID) "-1");

        if (loadOther) {
            int stickSize = stickIds.size() - 1;
            if (vo.getCurPage() >= stickSize / vo.getPageSize() + 1) {
                pageRequest.setStickOffset(stickSize);
            }
        }

        return getPage(pageRequest, (root, criteriaQuery, builder, predicates) -> {
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

    protected void setId(ID id, ENTITY entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw GlobalException.createError("no id field");
        }
    }

    protected ID getId(ENTITY entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return (ID) idField.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw GlobalException.createError("no id field");
        }
    }
}
