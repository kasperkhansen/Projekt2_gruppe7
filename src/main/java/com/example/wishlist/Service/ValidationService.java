package com.example.wishlist.Service;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class ValidationService {

    // validation rules in terms of constants:
    private static final int NAME_MAX_LENGTH = 15; // names = username, passwords, wishlistNames and item names
    private static final int EMAIL_MAX_LENGTH = 40;

    private static final int PASSWORD_MAX_LENGTH = 20;


    // ----------------- public methods -----------------
        // complex rule-check methods with combination of specific and individual rules

    public void validateNotNullInput(List<Object> objectList) throws IllegalArgumentException {
        validateNotNull(objectList);
    }

    public String validateName(String namePara) throws Exception {
        String name = cleanUpField(namePara);
        validateNonEmpty(name, "Name cannot be empty");
        validateLength(name, NAME_MAX_LENGTH, "Name '"+name+"' is too long");
        validateCharacterSet(name, "Name '"+name+"' contains invalid characters");
        return name;
    }

    public String validatePassword(String passwordPara) throws Exception {
        String password = cleanUpField(passwordPara);
        validateNonEmpty(password, "Password cannot be empty");
        validateLength(password, PASSWORD_MAX_LENGTH, "Passord is too long");
        validatePasswordFormat(password, "Password can only contain letters A-Z and numbers");
        return password;
    }

    public String validateEmail(String emailPara) throws Exception {
        String email = cleanUpField(emailPara);
        validateNonEmpty(email, "Email cannot be empty");
        validateLength(email, EMAIL_MAX_LENGTH, "Email '"+email+"' is too long");
        validateEmailFormat(email, "Email format '"+email+"' is invalid");
        return email;
    }

    public String validateURL(String urlPara) throws Exception {
        String url = cleanUpField(urlPara);
        validateNonEmpty(url, "URL cannot be empty");
        validateURLFormat(url, "URL '"+urlPara+"' format is invalid");
        return url;
    }

    public Double validatePrice(Double price) throws Exception {
        validatePriceLength(price, "Price should be between 0 and 1000000.0");
        return price;
    }




    // ----------------- rule methods -----------------
        // simple and very specific rule-checks
            // 1. check with if-else
            // 2. embeds the Exception with a specified detail message, if Exception thrown

        // not null
    private void validateNotNull(List<Object> objectList) {
        for (Object obj : objectList) {
            if (obj == null || (obj instanceof String && ((String) obj).isEmpty())) {
                throw new IllegalArgumentException("Parameters cannot be null or empty");
            }
            else if (obj instanceof Double && (Double)obj == 0) {
                throw new IllegalArgumentException("Price cannot be null or zero");
            }
        }
    }

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

    private void validatePriceLength(Double price, String message) {
        if(price < 0 || price > 1000000.0) {
            throw new IllegalArgumentException(message);
        }
    }

        // character set rules
    private void validateCharacterSet(String field, String message) throws IllegalArgumentException {
        if (!field.matches("[a-zA-Z0-9 ]+")) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validatePasswordFormat(String field, String message) throws IllegalArgumentException {
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

    private void validateURLFormat(String url, String message) throws IllegalArgumentException {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        try {
            new URL(url);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(message, ex);
        }
    }



    // ----------------- service methods -----------------

    private String cleanUpField(String name) {
        return name.trim();
    }

}
