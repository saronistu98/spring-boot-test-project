package com.saron.spring.test.address.dao;

import com.saron.spring.test.address.dto.AddressDto;
import com.saron.spring.test.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@ToString
@Table(name = "addresses")
public class AddressEntity extends BaseEntity {

    private String city;
    private String county;
    private String country;
    private String username;
    private String street;
    private short streetNumber;
    private String extraDetails;
    private boolean isMain;

    public static AddressEntity create(String username, AddressDto addressDto) {
        AddressEntity entity = new AddressEntity();
        entity.city = addressDto.getCity();
        entity.county = addressDto.getCounty();
        entity.country = addressDto.getCountry();
        entity.street = addressDto.getStreet();
        entity.streetNumber = addressDto.getStreetNumber();
        entity.extraDetails = addressDto.getExtraDetails();
        entity.username = username;
        return entity;
    }

    public void update(AddressDto addressDto) {
        this.city = addressDto.getCity();
        this.county = addressDto.getCounty();
        this.country = addressDto.getCountry();
        this.street = addressDto.getStreet();
        this.streetNumber = addressDto.getStreetNumber();
        this.extraDetails = addressDto.getExtraDetails();
    }

}
