package com.saron.spring.test.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerializedOrderRepository extends JpaRepository<SerializedOrderEntity, Long> {

    SerializedOrderEntity findByExternalId(String externalId);

}
