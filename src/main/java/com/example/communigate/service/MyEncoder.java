package com.example.communigate.service;

import java.util.Base64;

public class MyEncoder {

    public static String encodeValue(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    public static String decodeValue(final String encryptedValue) {
        return new String(Base64.getDecoder().decode(encryptedValue));
    }

    private MyEncoder() {
        throw new IllegalStateException("Utility class");
    }
}
