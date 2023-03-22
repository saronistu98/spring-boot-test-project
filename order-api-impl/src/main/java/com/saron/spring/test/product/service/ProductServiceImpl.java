package com.saron.spring.test.product.service;

import com.saron.spring.test.order.dto.PurchasedProductSubtractDto;
import com.saron.spring.test.order.dto.PurchasedProductSubtractResponseDto;
import com.saron.spring.test.product.dao.ProductEntity;
import com.saron.spring.test.product.dao.ProductRepository;
import com.saron.spring.test.product.dto.ProductDto;
import com.saron.spring.test.product.dto.ProductUpdateDto;
import com.saron.spring.test.product.exception.OutOfStockException;
import com.saron.spring.test.product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @RabbitListener(queues = "PurchasedProductQueue")
    public PurchasedProductSubtractResponseDto consumeCarouselItemDetailsRequest(PurchasedProductSubtractDto dto) {
        ProductEntity productEntity = getProduct(dto.getEan());
        productEntity.subtractQuantity(dto.getQuantity());
        productRepository.save(productEntity);
        PurchasedProductSubtractResponseDto responseDto = new PurchasedProductSubtractResponseDto();
        responseDto.setQuantityLeft(productEntity.getQuantity());
        responseDto.setName(productEntity.getName());
        return responseDto;
    }

    @RabbitListener(queues = "PurchasedProductQueueDeadLetter")
    public void consumeRejectedCarouselItemDetailsRequest(PurchasedProductSubtractDto dto) {
        log.warn("An error occurred: {}", dto);
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
