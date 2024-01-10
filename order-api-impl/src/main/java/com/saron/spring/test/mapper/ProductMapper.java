package com.saron.spring.test.mapper;

import com.saron.spring.test.product.dao.CachedProductEntity;
import com.saron.spring.test.product.dto.CachedProductDto;
import com.saron.spring.test.product.dto.ProductDto;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface ProductMapper {

    CachedProductDto entityToDto(CachedProductEntity entity);

    CachedProductDto productToCachedDto(ProductDto dto);

}
