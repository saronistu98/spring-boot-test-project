package com.saron.spring.test.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CachedProductRepository extends JpaRepository<CachedProductEntity, Long> {

    Optional<CachedProductEntity> findByNameAndEan(String name, String ean);

    @Modifying
    @Transactional
    @Query("UPDATE CachedProductEntity SET isLoadedIntoCache = FALSE")
    void setAllAsNotLoadedIntoCache();

    @Modifying
    @Transactional
    @Query("UPDATE CachedProductEntity SET isLoadedIntoCache = TRUE WHERE id IN ?1")
    void markAsLoadedIntoCacheByIds(List<Long> ids);

    @Query(value = "SELECT * FROM products_cache WHERE is_loaded_into_cache = FALSE LIMIT ?1", nativeQuery = true)
    List<CachedProductEntity> findAllNotLoadedIntoCacheWithLimit(int limit);

}
