package com.saron.spring.test.mapper;

import com.saron.spring.test.order.dao.OrderItemEntity;
import com.saron.spring.test.order.dto.OrderItemDto;
import com.saron.spring.test.order.pojo.OrderItem;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface OrderItemMapper {

    OrderItem dtoToPojo(OrderItemDto orderItemDto);

    OrderItemDto toOrderItemDto(OrderItemEntity orderItemEntity);

}
