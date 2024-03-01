package com.saron.spring.test.order.specification;

import com.saron.spring.test.order.dao.OrderEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class OrderSpecification2 implements Specification<OrderEntity> {

    @Override
    public Predicate toPredicate(Root<OrderEntity> root, @NonNull CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.notEqual(root.get("city"), "Hunedoara");
    }
}