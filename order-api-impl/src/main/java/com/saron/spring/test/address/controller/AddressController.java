package com.saron.spring.test.address.controller;

import com.saron.spring.test.address.dto.AddressDto;
import com.saron.spring.test.address.endpoint.AddressEndpoint;
import com.saron.spring.test.address.service.AddressService;
import com.saron.spring.test.base.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressController extends BaseController implements AddressEndpoint {

    private final AddressService addressService;

    @Override
    public long create(AddressDto addressDto) {
        String username = getLoggedUserUsername();
        return addressService.create(username, addressDto);
    }

    @Override
    public void update(AddressDto addressDto, long id) {
        addressService.update(addressDto, id);
    }

    @Override
    public void delete(long id) {
        addressService.delete(id);
    }

}
