package com.thatday.user.repository;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class JPAUtil {

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
}
