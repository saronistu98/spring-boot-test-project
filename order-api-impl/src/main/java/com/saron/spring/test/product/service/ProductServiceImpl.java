package com.saron.spring.test.product.service;

import com.saron.spring.test.product.dao.ProductEntity;
import com.saron.spring.test.product.dao.ProductRepository;
import com.saron.spring.test.product.dto.ProductDto;
import com.saron.spring.test.product.dto.ProductUpdateDto;
import com.saron.spring.test.product.exception.OutOfStockException;
import com.saron.spring.test.product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void updateStock(List<ProductUpdateDto> updates) {
        updates.forEach(update -> {
            ProductEntity product = getProduct(update.getId());
            product.setQuantity(update.getQuantity());
        });
    }

    @Override
    @Transactional
    public ProductDto purchase(Long productId, int quantity) {
        ProductEntity product = getProduct(productId);
        if (product.getQuantity() < quantity)
            throw new OutOfStockException(productId);
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        return product.toDto();
    }

    @Override
    public Long create(ProductDto productDto) {
        ProductEntity entity = ProductEntity.create(productDto);
        productRepository.save(entity);
        return entity.getId();
    }

    @Override
    public List<ProductDto> getAll(String name) {
        return productRepository.findAllByName(name).stream()
                .map(ProductEntity::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(String ean) {
        productRepository.deleteByEan(ean);
    }

    private ProductEntity getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

}
