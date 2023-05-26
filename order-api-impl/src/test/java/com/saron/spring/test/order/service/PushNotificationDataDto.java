package com.saron.spring.test.order.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PushNotificationDataDto {

    private String type;
    private String awb;
    private String text;
    private Map<String, String> map;


}
