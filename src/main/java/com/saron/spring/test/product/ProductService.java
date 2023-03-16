package com.saron.spring.test.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final EntityManager entityManager;
    private final TransactionTemplate transactionTemplate;

    @Transactional
    public void updateStock(List<ProductUpdateDto> updates) {
        updates.forEach(update -> {
            System.out.println("UPDATE STOCK: Fetching product with ID " + update.getId() + " at " + LocalTime.now());
            ProductEntity product = productRepository.findAllById(update.getId()).get(0);
            System.out.println("UPDATE STOCK: Product with ID " + update.getId() + " fetched at " + LocalTime.now());
            product.setQuantity(update.getQuantity());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("UPDATE STOCK: Saving product with ID " + update.getId() + " at: " + LocalTime.now());
            productRepository.save(product);
            entityManager.flush();
            transactionTemplate.executeWithoutResult(status -> entityManager.clear());
            System.out.println("UPDATE STOCK: Product with ID " + update.getId() + " saved at: " + LocalTime.now());
        });
    }

    @Transactional
    public ProductDto purchase(Long productId, int quantity) {
        System.out.println("PURCHASE STOCK: Fetching product with ID " + productId + " at " + LocalTime.now());
        ProductEntity product = getProduct(productId);
        System.out.println("PURCHASE STOCK: Product with ID " + productId + " fetched at " + LocalTime.now());
        if (product.getQuantity() < quantity)
            throw new OutOfStockException(productId);
        product.setQuantity(product.getQuantity() - quantity);
        System.out.println("PURCHASE STOCK: Saving product with ID " + productId + " at: " + LocalTime.now());
        productRepository.save(product);
        System.out.println("PURCHASE STOCK: Product with ID " + productId + " saved at: " + LocalTime.now());
        return product.toDto();
    }

    private ProductEntity getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public Long create(ProductDto productDto) {
        ProductEntity entity = ProductEntity.create(productDto);
        productRepository.save(entity);
        return entity.getId();
    }

    public List<ProductDto> getAll(String name) {
        return productRepository.findAllByName(name).stream()
                .map(ProductEntity::toDto)
                .collect(Collectors.toList());
    }

}
