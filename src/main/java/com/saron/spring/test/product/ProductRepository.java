package com.saron.spring.test.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Lock(PESSIMISTIC_WRITE)
    Optional<ProductEntity> findById(@NonNull Long id);

    @Lock(PESSIMISTIC_WRITE)
    List<ProductEntity> findAllById(Long id);

    List<ProductEntity> findAllByName(String name);

}
