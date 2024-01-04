package com.saron.spring.test.address.service;

import com.saron.spring.test.address.dto.AddressDto;

public interface AddressService {

    long create(String user, AddressDto addressDto);

    void update(AddressDto addressDto, long id);

    void delete(long id);

}
