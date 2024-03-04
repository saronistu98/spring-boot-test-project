package com.saron.spring.test.serialization;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CypherUtils {

    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public String encrypt(String input, int lettersToSkip) {
        char[] output = input.toCharArray();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (Character.isDigit(currentChar) || Character.isAlphabetic(currentChar)) {
                boolean isUppercase = Character.isUpperCase(currentChar);
                int desiredIndex = ALPHABET.indexOf(Character.toLowerCase(currentChar)) + lettersToSkip;
                if (desiredIndex > ALPHABET.length() - 1)
                    desiredIndex = desiredIndex - ALPHABET.length();
                char letterReplacement = ALPHABET.charAt(desiredIndex);
                output[i] = isUppercase ? Character.toUpperCase(letterReplacement) : ALPHABET.charAt(desiredIndex);
            }
        }
        return new String(output);
    }
}
