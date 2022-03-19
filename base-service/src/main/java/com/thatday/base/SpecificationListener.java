package com.thatday.base;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public interface SpecificationListener {

    void addSpecification(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder builder, List<Predicate> plist);
}
