package com.saron.spring.test.order.specification;

import com.saron.spring.test.order.dao.OrderEntity;
import com.saron.spring.test.order.dto.OrderSearchSort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.*;

import static com.saron.spring.test.order.enums.OrderStatus.NEW;

@RequiredArgsConstructor
public class OrderSpecification implements Specification<OrderEntity> {

    private final OrderSearchSort sort;

    @Override
    public Predicate toPredicate(Root<OrderEntity> root, @NonNull CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Order orderByCity = criteriaBuilder.desc(criteriaBuilder.selectCase()
                .when(criteriaBuilder.equal(root.get("city"), sort.getCity()), 1)
                .otherwise(0));
        Order orderByCreatedDateDesc = criteriaBuilder.desc(root.get("createDate"));
        query.orderBy(orderByCity, orderByCreatedDateDesc);
        return criteriaBuilder.equal(root.get("status"), NEW);
    }

}