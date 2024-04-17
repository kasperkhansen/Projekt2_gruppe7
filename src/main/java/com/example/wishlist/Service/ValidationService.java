package com.example.wishlist.Service;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ValidationService {

    // validation rules in terms of constants:
    private static final int NAME_MAX_LENGTH = 15; // names = username, passwords, wishlistNames and item names
    private static final int EMAIL_MAX_LENGTH = 40;


    // ----------------- public methods -----------------
        // complex rule-check methods with combination of specific and individual rules

    public String validateName(String namePara) throws Exception {
        String name = cleanUpField(namePara);
        validateNonEmpty(name, "Name cannot be empty");
        validateLength(name, NAME_MAX_LENGTH, "Name is too long");
        validateCharacterSet(name, "Name contains invalid characters");
        return name;
    }

    public String validateEmail(String emailPara) throws Exception {
        String email = cleanUpField(emailPara);
        validateNonEmpty(email, "Email cannot be empty");
        validateLength(email, EMAIL_MAX_LENGTH, "Email is too long");
        validateEmailFormat(email, "Email format is invalid");
        return email;
    }


    public String validateURL(String urlPara) throws Exception {
        String url = cleanUpField(urlPara);
        validateNonEmpty(url, "URL cannot be empty");
        validateURLFormat(url);
        return url;
    }

    public Double validatePrice(Double price) throws Exception {
        validatePriceLength(price);
        return price;
    }




    // ----------------- rule methods -----------------
        // simple and very specific rule-checks
            // 1. check with if-else
            // 2. embeds the Exception with a specified detail message, if Exception thrown


        // non-empty
    private void validateNonEmpty(String field, String message) throws IllegalArgumentException {
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

        // length rules
    private void validateLength(String field, int maxLength, String message) throws IllegalArgumentException {
        if (field.length() > maxLength) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validatePriceLength(Double price) {
        if(price < 0 || price > 1000000.0) {
            throw new IllegalArgumentException("Price should be between 0 and 1000000.0");
        }
    }

        // character set rules
    private void validateCharacterSet(String field, String message) throws IllegalArgumentException {
        if (!field.matches("[a-zA-Z0-9 ]+")) {
            throw new IllegalArgumentException(message);
        }
    }

        // format rules
    private void validateEmailFormat(String field, String message) throws IllegalArgumentException {
        if (!field.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b")) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateURLFormat(String url) throws IllegalArgumentException {
        try {
            new URL(url);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException("URL format is invalid", ex);
        }
    }



    // ----------------- service methods -----------------

    private String cleanUpField(String name) {
        return name.trim();
    }

}
