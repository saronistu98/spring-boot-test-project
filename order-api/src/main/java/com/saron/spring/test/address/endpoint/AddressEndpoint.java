package com.saron.spring.test.address.endpoint;

import com.saron.spring.test.address.dto.AddressDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/address")
public interface AddressEndpoint {

    @PostMapping(path = "/create")
    long create(@RequestBody @Valid AddressDto addressDto);

    @PutMapping(path = "/{id}")
    void update(@RequestBody @Valid AddressDto addressDto, @PathVariable long id);

    @DeleteMapping(path = "/{id}")
    void delete(@PathVariable long id);

}
