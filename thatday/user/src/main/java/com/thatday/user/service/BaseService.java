package com.thatday.user.service;

import com.thatday.common.model.PageInfoVo;
import com.thatday.common.model.PageResult;
import com.thatday.common.utils.TemplateCodeUtil;
import com.thatday.user.repository.BaseDao;
import com.thatday.user.repository.JPAUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface BaseService<ENTITY, ID, DAO extends BaseDao<ENTITY, ID>> {

    //可自己定义id
    ID customDatabaseId();

    DAO getDao();

    ENTITY getOne(ID id);

    ENTITY getOneAndCheckNull(ID id, String msg);

    void saveOrUpdate(ENTITY entity);

    <S extends ENTITY> List<S> saveAll(Iterable<S> list);

    /**
     * 获取DTO分页列表
     */
    <TARGET> PageResult<TARGET> getPageDTOList(PageRequest pageRequest, Class<TARGET> targetClass,
                                               TemplateCodeUtil.OnTransListener<TARGET, ENTITY> transDTOListener,
                                               JPAUtil.SpecificationListener otherConditionListener);

    /**
     * 获取Entity分页列表
     */
    Page<ENTITY> getPageList(PageRequest pageRequest, JPAUtil.SpecificationListener otherConditionListener);

    /**
     * 获取DTO顶置数据列表
     */
    <TARGET> PageResult<TARGET> getStickDTOList(PageInfoVo vo, @NotNull Set<ID> stickIds, Class<TARGET> targetClass,
                                                TemplateCodeUtil.OnTransListener<TARGET, ENTITY> stickDTOListener,
                                                TemplateCodeUtil.OnTransListener<TARGET, ENTITY> otherDTOListener,
                                                JPAUtil.SpecificationListener otherConditionListener);


}
