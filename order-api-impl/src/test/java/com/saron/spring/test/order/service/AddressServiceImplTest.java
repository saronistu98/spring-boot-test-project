package com.saron.spring.test.order.service;

import com.saron.spring.test.order.dao.OrderEntity;
import com.saron.spring.test.order.dao.OrderRepository;
import com.saron.spring.test.order.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Captor
    private ArgumentCaptor<OrderEntity> orderEntityCaptor;

    @Test
    public void givenOrderDto_expectCreateAndSave() {
        when(orderRepository.count()).thenReturn(413L);
        OrderDto orderDto = getOrderDto();
        orderService.create(orderDto);
        verify(orderRepository).save(orderEntityCaptor.capture());
        OrderEntity capture = orderEntityCaptor.getValue();
        assertEquals(orderDto.getCity(), capture.getCity());
        assertEquals(orderDto.getCounty(), capture.getCounty());
        assertEquals(orderDto.getItemsPrice(), capture.getItemsPrice());
        assertEquals(orderDto.getDeliveryPrice(), capture.getDeliveryPrice());
    }

    private OrderDto getOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setCity("Hunedoara");
        orderDto.setCounty("Hunedoara");
        orderDto.setItemsPrice(430);
        orderDto.setDeliveryPrice(100);
        return orderDto;
    }

}
