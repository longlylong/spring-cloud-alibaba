package com.thatday.base;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PredicateUtil {

    public static Predicate isNotDel(Root<?> root, CriteriaBuilder builder) {
        return builder.equal(root.get("is_del"), false);
    }
}
