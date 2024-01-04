package com.saron.spring.test.serialization;

public interface SerializationService {

    byte[] serialize(Object o);

    Object deserialize(byte[] s, Class<?> clazz);

}
