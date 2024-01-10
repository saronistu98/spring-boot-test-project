package com.saron.spring.test.product.service;

import com.saron.spring.test.order.dto.PurchasedProductSubtractDto;
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
                .toList();
    }

    @Override
    @Transactional
    public void delete(String ean) {
        productRepository.deleteByEan(ean);
    }

    @Override
    public void subtractPurchasedQuantity(PurchasedProductSubtractDto dto) {
        ProductEntity productEntity = getProduct(dto.getEan());
        productEntity.subtractQuantity(dto.getQuantity());
        productRepository.save(productEntity);
    }

    private ProductEntity getProduct(String ean) {
        return productRepository.findByEan(ean)
                .orElseThrow(() -> new ProductNotFoundException(ean));
    }

    private ProductEntity getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

}
