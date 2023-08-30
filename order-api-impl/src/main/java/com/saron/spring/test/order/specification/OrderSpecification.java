package com.saron.spring.test.order.specification;

import com.saron.spring.test.order.dao.OrderEntity;
import com.saron.spring.test.order.dao.OrderItemEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

import static com.saron.spring.test.order.enums.ItemType.PRODUCT;

public class OrderSpecification implements Specification<OrderEntity> {

    @Override
    public Predicate toPredicate(Root<OrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        root.join("items", JoinType.LEFT);
        Subquery<OrderItemEntity> subquery = query.subquery(OrderItemEntity.class);
        Root<OrderItemEntity> subqueryRoot = subquery.from(OrderItemEntity.class);
        subquery.select(subqueryRoot);
        subquery.where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(subqueryRoot.get("type"), PRODUCT),
                        criteriaBuilder.equal(subqueryRoot.get("order"), root)
                )
        );
        query.where(criteriaBuilder.exists(subquery));
        return query.getRestriction();
    }
}