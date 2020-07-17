package com.thatday.user.repository;

import com.thatday.common.model.PageInfoVo;
import com.thatday.common.model.PageResult;
import com.thatday.common.utils.TemplateCodeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class JPAUtil {

    public static PageRequest prIdDesc(PageInfoVo vo) {
        return prIdDesc(vo.getCurPage(), vo.getPageSize());
    }

    public static PageRequest prUpdateTimeDesc(PageInfoVo vo) {
        return prUpdateTimeDesc(vo.getCurPage(), vo.getPageSize());
    }

    public static PageRequest prCreateTimeDesc(PageInfoVo vo) {
        return prCreateTimeDesc(vo.getCurPage(), vo.getPageSize());
    }

    public static PageRequest prIdDesc(Integer curPage, Integer pageSize) {
        return setPR(curPage, pageSize, Sort.Direction.DESC, "id");
    }

    public static PageRequest prUpdateTimeDesc(Integer curPage, Integer pageSize) {
        return setPR(curPage, pageSize, Sort.Direction.DESC, "createTime");
    }

    public static PageRequest prCreateTimeDesc(Integer curPage, Integer pageSize) {
        return setPR(curPage, pageSize, Sort.Direction.DESC, "createTime");
    }

    private static PageRequest setPR(Integer curPage, Integer pageSize, Sort.Direction direction, String... properties) {
        Sort sort = Sort.by(direction, properties);
        return PageRequest.of(curPage, pageSize, sort);
    }

    public static <T> PageResult<T> setPageResult(List<T> list) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCurPage(0);
        pageResult.setTotalCount((long) list.size());
        pageResult.setTotalPage(1);
        pageResult.setDataList(list);
        return pageResult;
    }

    public static <T> PageResult<T> setPageResult(Integer curPage, Page<T> fromPage) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCurPage(curPage);
        pageResult.setTotalCount(fromPage.getTotalElements());
        pageResult.setTotalPage(fromPage.getTotalPages());
        pageResult.setDataList(fromPage.getContent());
        return pageResult;
    }

    public static <T, Y> PageResult<T> setPageResult(Integer curPage, Page<Y> fromPage, Class<T> clazz) {
        return setPageResult(curPage, fromPage, clazz, null);
    }

    public static <T, Y> PageResult<T> setPageResult(Integer curPage, Page<Y> fromPage, Class<T> clazz, TemplateCodeUtil.OnTransListener<T, Y> onTransListener) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCurPage(curPage);
        pageResult.setTotalCount(fromPage.getTotalElements());
        pageResult.setTotalPage(fromPage.getTotalPages());

        List<T> transTo = TemplateCodeUtil.transTo(fromPage.getContent(), clazz, onTransListener);
        pageResult.setDataList(transTo);

        return pageResult;
    }

    public static <T> Specification<T> makeSpecification(SpecificationListener listener) {
        Specification<T> specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder builder) {
                List<Predicate> predicates = new ArrayList<>();
                if (listener != null) {
                    listener.addSpecification(root, criteriaQuery, builder, predicates);
                }
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return specification;
    }

    public interface SpecificationListener {
        void addSpecification(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder builder, List<Predicate> predicates);
    }

    public static class StickPageRequest extends PageRequest {

        long stickOffset = 0;

        public void setStickOffset(long stickOffset) {
            this.stickOffset = stickOffset;
        }

        @Override
        public long getOffset() {
            return super.getOffset() - stickOffset;
        }

        public StickPageRequest(PageInfoVo vo) {
            super(vo.getCurPage(), vo.getPageSize(), Sort.by(Sort.Direction.DESC, "createTime"));
        }

        public StickPageRequest(int page, int size, Sort sort) {
            super(page, size, sort);
        }
    }
}
