package com.saron.spring.test.serialization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CypherUtilsTest {

    @Test
    void givenInput_expectCorrectOutput() {
        String result = CypherUtils.encrypt("I love you so much", 2);
        assertEquals("K nqxg aqw uq owej", result);
    }

}
