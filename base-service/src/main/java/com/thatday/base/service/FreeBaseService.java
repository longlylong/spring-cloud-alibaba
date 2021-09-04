package com.thatday.base.service;

import com.thatday.base.SpecificationListener;
import com.thatday.base.repository.BaseDao;
import com.thatday.common.model.PageInfoVo;
import com.thatday.common.model.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public interface FreeBaseService<ENTITY, ID, DAO extends BaseDao<ENTITY, ID>> {

    //可自己定义id
    //自增返回空 id字段需要加上注解@GeneratedValue(strategy = GenerationType.IDENTITY)
    ID customDatabaseId();

    DAO getDao();

    ENTITY getOne(ID id);

    ENTITY getOneAndCheckNull(ID id, String prefix);

    ENTITY getLastOneById();

    void saveOrUpdate(ENTITY entity);

    <S extends ENTITY> List<S> saveAll(Iterable<S> list);

    long countAll(SpecificationListener specificationListener);

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
                                      BiConsumer<TARGET, ENTITY> transDTOListener);

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
                                                   BiConsumer<TARGET, ENTITY> transDTOListener);

    /**
     * 获取DTO顶置分页列表
     */
    <TARGET> PageResult<TARGET> getStickPageResultToDTO(PageInfoVo vo, @NotNull Set<ID> stickIds, Class<TARGET> targetClass,
                                                        SpecificationListener otherConditionListener,
                                                        BiConsumer<TARGET, ENTITY> stickDTOListener,
                                                        BiConsumer<TARGET, ENTITY> otherDTOListener);

}
