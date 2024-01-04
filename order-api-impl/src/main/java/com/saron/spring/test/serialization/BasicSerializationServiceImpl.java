package com.saron.spring.test.serialization;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;

@Component
public class BasicSerializationServiceImpl implements SerializationService {

    @Override
    public byte[] serialize(Object o) {
        validateClass(o.getClass());
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(o);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object deserialize(byte[] s, Class<?> clazz) {
        validateClass(clazz);
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(s);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateClass(Class<?> clazz) {
        Arrays.stream(clazz.getInterfaces())
                .filter(i -> i == Serializable.class)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

}
