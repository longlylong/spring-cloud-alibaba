package com.thatday.user.service;

import com.thatday.common.model.PageInfoVo;
import com.thatday.common.model.PageResult;
import com.thatday.common.utils.TemplateCodeUtil;
import com.thatday.user.repository.BaseDao;
import com.thatday.user.repository.BaseEntity;
import com.thatday.user.repository.JPAUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface BaseService<ENTITY extends BaseEntity, ID, DAO extends BaseDao<ENTITY, ID>> {

    //可自己定义id
    ID customDatabaseId();

    DAO getDao();

    ENTITY getOne(ID id);

    ENTITY getOneAndCheckNull(ID id, String msg);

    ENTITY getLastOneById();

    void saveOrUpdate(ENTITY entity);

    <S extends ENTITY> List<S> saveAll(Iterable<S> list);

    /**
     * 获取全部数据
     */
    List<ENTITY> getAllList(JPAUtil.SpecificationListener otherConditionListener);

    /**
     * 获取TARGET全部列表
     */
    <TARGET> List<TARGET> getAllDTOList(Class<TARGET> targetClass,
                                        JPAUtil.SpecificationListener otherConditionListener,
                                        TemplateCodeUtil.OnTransListener<TARGET, ENTITY> transDTOListener);

    /**
     * 获取Entity分页列表
     */
    Page<ENTITY> getPageList(PageRequest pageRequest, JPAUtil.SpecificationListener otherConditionListener);

    /**
     * 获取Entity分页列表
     */
    PageResult<ENTITY> getPageResultList(PageRequest pageRequest, JPAUtil.SpecificationListener otherConditionListener);

    /**
     * 获取DTO分页列表
     */
    <TARGET> PageResult<TARGET> getPageResultDTOList(PageRequest pageRequest, Class<TARGET> targetClass,
                                                     JPAUtil.SpecificationListener otherConditionListener,
                                                     TemplateCodeUtil.OnTransListener<TARGET, ENTITY> transDTOListener);

    /**
     * 获取DTO顶置分页列表
     */
    <TARGET> PageResult<TARGET> getPageResultStickDTOList(PageInfoVo vo, @NotNull Set<ID> stickIds, Class<TARGET> targetClass,
                                                          JPAUtil.SpecificationListener otherConditionListener,
                                                          TemplateCodeUtil.OnTransListener<TARGET, ENTITY> stickDTOListener,
                                                          TemplateCodeUtil.OnTransListener<TARGET, ENTITY> otherDTOListener);

}
