package com.saron.spring.test.order.service;

import com.saron.spring.test.order.dao.SerializedOrderEntity;
import com.saron.spring.test.order.dao.SerializedOrderRepository;
import com.saron.spring.test.order.pojo.Order;
import com.saron.spring.test.serialization.SerializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderSerializationServiceImpl implements OrderSerializationService {

    private final SerializationService serializationService;
    private final SerializedOrderRepository serializedOrderRepository;

    @Override
    public void save(Order order) {
        byte[] serial = serializationService.serialize(order);
        SerializedOrderEntity entity = SerializedOrderEntity.create(order.getExternalId(), serial);
        serializedOrderRepository.save(entity);
    }

    @Override
    public Order get(String externalId) {
        SerializedOrderEntity entity = serializedOrderRepository.findByExternalId(externalId);
        return (Order) serializationService.deserialize(entity.getValue(), Order.class);
    }

}
