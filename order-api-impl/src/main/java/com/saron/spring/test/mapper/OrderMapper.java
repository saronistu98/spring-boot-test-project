package com.saron.spring.test.mapper;

import com.saron.spring.test.order.dao.OrderEntity;
import com.saron.spring.test.order.dto.OrderDto;
import com.saron.spring.test.order.dto.PlacedOrderDto;
import com.saron.spring.test.order.pojo.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR, uses = OrderItemMapper.class)
public abstract class OrderMapper {

    @Mapping(target = "externalId", ignore = true)
    public abstract Order dtoToPojo(OrderDto orderDto);

    public abstract PlacedOrderDto toPlacedOrderDto(OrderEntity orderEntity);

}
