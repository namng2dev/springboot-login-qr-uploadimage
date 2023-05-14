package com.bank.manager.util;

import org.apache.commons.lang3.RandomStringUtils;

/*
Generate a random password of 6 characters including at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 symbol
*/
public class PasswordGenerator {
    public static String generatedPassword() {
        String uppercaseString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseString = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "!@#$%^&*()";

        // Select per type 1 character
        String uppercase = RandomStringUtils.random(1, uppercaseString);
        String lowercase = RandomStringUtils.random(1, lowercaseString);
        String digit = RandomStringUtils.random(1, digits);
        String symbol = RandomStringUtils.random(1, symbols);

        // Get more 2 characters for 6 characters
        String randomString = RandomStringUtils.random(2, uppercaseString + lowercaseString + digits + symbols);

        String password = uppercase + lowercase + digit + symbol + randomString;

        return password;
    }
}