package com.chatlucid.utils;

import java.security.SecureRandom;

public class Generator {

    private static final SecureRandom secureRandom = new SecureRandom();

    private Generator() {

    }

    public static String generateNumber(int length) {
        StringBuilder otp = new StringBuilder();

        while (otp.length() < length) {
            int randomNumber = secureRandom.nextInt(10);
            otp.append(randomNumber);
        }

        return otp.toString();
    }
}