package com.saron.spring.test.order.service;

import com.saron.spring.test.order.pojo.Order;

public interface OrderSerializationService {

    void save(Order order);

    Order get(String externalId);

}
