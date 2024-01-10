package com.saron.spring.test.product.service;

import com.saron.spring.test.mapper.ProductMapper;
import com.saron.spring.test.product.dao.CachedProductEntity;
import com.saron.spring.test.product.dao.CachedProductRepository;
import com.saron.spring.test.product.dto.CachedProductDto;
import com.saron.spring.test.product.dto.CachedProductUpdateDto;
import com.saron.spring.test.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCachingServiceImpl implements ProductCachingService {

    private final ProductMapper productMapper;
    private final ProductESService productESService;
    private final CachedProductRepository cachedProductRepository;
    private final Map<String, CachedProductDto> cache = new HashMap<>();
    private final List<String> listt = new ArrayList<>();

    @EventListener(classes = ApplicationReadyEvent.class)
    public void loadProductsIntoCache() {
        log.info("Product cache initialization started!");
        cachedProductRepository.setAllAsNotLoadedIntoCache();
        List<CachedProductEntity> list = cachedProductRepository.findAllNotLoadedIntoCacheWithLimit(1000);
        while (!list.isEmpty()) {
            Map<String, CachedProductDto> keyProductMap = list.stream()
                    .collect(Collectors.toMap(entity -> getCacheKey(entity.getName(), entity.getEan()), productMapper::entityToDto));
            cache.putAll(keyProductMap);
            List<Long> entitiesIds = list.stream()
                    .map(CachedProductEntity::getId)
                    .toList();
            cachedProductRepository.markAsLoadedIntoCacheByIds(entitiesIds);
            list = cachedProductRepository.findAllNotLoadedIntoCacheWithLimit(1000);
        }
        log.info("Product cache initialization finished!");
    }

    @Override
    @Transactional
    public void invalidate() {
        log.info("Product cache invalidation started!");
        cache.clear();
        cachedProductRepository.deleteAll();
        log.info("Product cache invalidation finished!");
    }

    @Override
    public CachedProductDto get(String name, String ean) {
        String key = getCacheKey(name, ean);
        return Optional.ofNullable(cache.get(key))
                .orElseGet(() -> getFromElasticSearchAndSave(key, name, ean));
    }

    @Override
    public void update(CachedProductUpdateDto dto) {
        String key = getCacheKey(dto.getName(), dto.getEan());
        CachedProductDto cachedProduct = cache.get(key);
        if (cachedProduct == null)
            return;
        Optional<CachedProductEntity> optionalEntity = find(dto);
        optionalEntity.ifPresentOrElse(entity -> {
            entity.update(dto);
            CachedProductDto productForCache = productMapper.entityToDto(entity);
            cachedProductRepository.save(entity);
            cache.put(key, productForCache);
        }, () -> log.debug("Product with name \"{}\" and EAN \"{}\" was found in the cache but not in the repository!", dto.getName(), dto.getEan()));
    }

    private Optional<CachedProductEntity> find(CachedProductUpdateDto dto) {
        return cachedProductRepository.findByNameAndEan(dto.getName(), dto.getEan());
    }

    private CachedProductDto getFromElasticSearchAndSave(String key, String name, String ean) {
        System.out.println(listt.contains(key));
        listt.add(key);
        ProductDto productDto = productESService.get(name, ean);
        CachedProductDto dto = productMapper.productToCachedDto(productDto);
        cache.put(key, dto);
        createAndSaveCachedProductEntity(productDto);
        return dto;
    }

    private void createAndSaveCachedProductEntity(ProductDto productDto) {
        CachedProductEntity entity = CachedProductEntity.create(productDto);
        cachedProductRepository.save(entity);
    }

    private String getCacheKey(String name, String ean) {
        return String.format("%s#%s", ean, name);
    }

}
