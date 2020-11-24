package com.thatday.user.service;

import com.thatday.common.model.PageInfoVo;
import com.thatday.common.model.PageResult;
import com.thatday.common.utils.BeanUtil;
import com.thatday.user.repository.BaseDao;
import com.thatday.user.repository.BaseEntity;
import com.thatday.user.repository.SpecificationListener;
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

    ENTITY getOneAndCheckNull(ID id, String prefix);

    ENTITY getLastOneById();

    void saveOrUpdate(ENTITY entity);

    <S extends ENTITY> List<S> saveAll(Iterable<S> list);

    /**
     * 获取全部数据
     */
    List<ENTITY> getAll();

    /**
     * 获取全部数据
     */
    List<ENTITY> getAll(SpecificationListener otherConditionListener);

    /**
     * 获取TARGET全部列表
     */
    <TARGET> List<TARGET> getAllToDTO(Class<TARGET> targetClass,
                                      SpecificationListener otherConditionListener,
                                      BeanUtil.OnTransListener<TARGET, ENTITY> transDTOListener);

    /**
     * 获取Entity分页列表
     */
    Page<ENTITY> getPage(PageRequest pageRequest, SpecificationListener otherConditionListener);

    /**
     * 获取Entity分页列表
     */
    PageResult<ENTITY> getPageResult(PageRequest pageRequest, SpecificationListener otherConditionListener);

    /**
     * 获取DTO分页列表
     */
    <TARGET> PageResult<TARGET> getPageResultToDTO(PageRequest pageRequest, Class<TARGET> targetClass,
                                                   SpecificationListener otherConditionListener,
                                                   BeanUtil.OnTransListener<TARGET, ENTITY> transDTOListener);

    /**
     * 获取DTO顶置分页列表
     */
    <TARGET> PageResult<TARGET> getStickPageResultToDTO(PageInfoVo vo, @NotNull Set<ID> stickIds, Class<TARGET> targetClass,
                                                        SpecificationListener otherConditionListener,
                                                        BeanUtil.OnTransListener<TARGET, ENTITY> stickDTOListener,
                                                        BeanUtil.OnTransListener<TARGET, ENTITY> otherDTOListener);

}
