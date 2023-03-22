package com.saron.spring.test.address.service;

import com.saron.spring.test.address.dao.AddressEntity;
import com.saron.spring.test.address.dao.AddressRepository;
import com.saron.spring.test.address.dto.AddressDto;
import com.saron.spring.test.address.exception.AddressNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public long create(String username, AddressDto addressDto) {
        AddressEntity addressEntity = AddressEntity.create(username, addressDto);
        addressRepository.save(addressEntity);
        return addressEntity.getId();
    }

    @Override
    public void update(AddressDto addressDto, long id) {
        AddressEntity addressEntity = getOrderEntity(id);
        addressEntity.update(addressDto);
        addressRepository.save(addressEntity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        addressRepository.deleteById(id);
    }

    private AddressEntity getOrderEntity(long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }

}
