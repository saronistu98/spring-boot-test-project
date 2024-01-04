package com.saron.spring.test.order.dao;

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
@Table(name = "serialized_orders")
public class SerializedOrderEntity extends BaseEntity {

    private String externalId;
    private byte[] value;

    public static SerializedOrderEntity create(String externalId, byte[] value) {
        SerializedOrderEntity entity = new SerializedOrderEntity();
        entity.setExternalId(externalId);
        entity.setValue(value);
        return entity;
    }

}
