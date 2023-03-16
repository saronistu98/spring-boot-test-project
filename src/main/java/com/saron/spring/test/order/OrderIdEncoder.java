package com.saron.spring.test.order;

import lombok.experimental.UtilityClass;
import org.hashids.Hashids;

@UtilityClass
public class OrderIdEncoder {

    private static final String SALT = "Saron";
    private static final String CHARACTER_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int MIN_HASH_LENGTH = 8;
    private static final int MAX_LENGTH = 8;

    public String encode(long id) {
        Hashids hashids = new Hashids(SALT, MIN_HASH_LENGTH, CHARACTER_SET);
        return hashids.encode(id, System.currentTimeMillis()).substring(0, MAX_LENGTH);
    }

}
