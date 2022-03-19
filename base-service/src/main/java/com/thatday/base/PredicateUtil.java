package com.thatday.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class PredicateUtil {

    public static Predicate isNotDel(Root<?> root, CriteriaBuilder builder) {
        return builder.equal(root.get("is_del"), false);
    }

    public static void addEqualUid(List<Predicate> predicates, Root<?> root, CriteriaBuilder builder, String uid) {
        ifAddEqual(predicates, root, builder, "uid", uid);
    }

    public static void addEqualBookId(List<Predicate> predicates, Root<?> root, CriteriaBuilder builder, String bookId) {
        ifAddEqual(predicates, root, builder, "bookId", bookId);
    }

    public static void ifAddEqual(List<Predicate> predicates, Root<?> root, CriteriaBuilder builder, EnumPath<?> field, Object params) {
        ifAddEqual(predicates, root, builder, field.getMetadata().getName(), params);
    }

    public static void ifAddEqual(List<Predicate> predicates, Root<?> root, CriteriaBuilder builder, StringPath field, Object params) {
        ifAddEqual(predicates, root, builder, field.getMetadata().getName(), params);
    }

    public static void ifAddEqual(List<Predicate> predicates, Root<?> root, CriteriaBuilder builder, String field, Object params) {
        if (params == null) {
            return;
        }
        if (params instanceof String && StrUtil.isBlank(params.toString())) {
            return;
        }
        predicates.add(builder.equal(root.get(field), params));
    }

    public static void ifAddLike(List<Predicate> predicates, Root<?> root, CriteriaBuilder builder, StringPath field, Object params) {
        ifAddLike(predicates, root, builder, field.getMetadata().getName(), params);
    }

    public static void ifAddLike(List<Predicate> predicates, Root<?> root, CriteriaBuilder builder, String field, Object params) {
        if (params == null) {
            return;
        }
        if (params instanceof String && StrUtil.isBlank(params.toString())) {
            return;
        }
        predicates.add(builder.like(root.get(field), "%" + params + "%"));
    }

    public static void ifAddIn(List<Predicate> predicates, Root<?> root, EnumPath field, Collection<?> params) {
        ifAddIn(predicates, root, field.getMetadata().getName(), params);
    }

    public static void ifAddIn(List<Predicate> predicates, Root<?> root, StringPath field, Collection<?> params) {
        ifAddIn(predicates, root, field.getMetadata().getName(), params);
    }

    public static void ifAddIn(List<Predicate> predicates, Root<?> root, String field, Collection<?> params) {
        if (params == null) {
            return;
        }
        if (CollUtil.isEmpty(params)) {
            return;
        }
        predicates.add(root.get(field).in(params));
    }
}
