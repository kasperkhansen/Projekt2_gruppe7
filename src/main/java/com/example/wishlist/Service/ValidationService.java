package com.example.wishlist.Service;

import com.example.wishlist.Model.DTO;
import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Repository.WishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ValidationService {

    // validation rules in terms of constants:
    private static final int NAME_MAX_LENGTH = 15; // names = username, passwords, wishlistNames and item names
    private static final int EMAIL_MAX_LENGTH = 40;
    private static final int PASSWORD_MAX_LENGTH = 64; // hash algorithm SHA-256 = 64 characters

    @Autowired
    private final WishRepo wishRepo;
    @Autowired
    private final WishService wishService;

    public ValidationService(WishRepo wishRepo, WishService wishService) {
        this.wishRepo = wishRepo;
        this.wishService = wishService;
    }


    // ----------------- public methods -----------------

    public DTO validateAndAddAttributes(Map<String, Object> requiredParameters, DTO dto) throws Exception {

        System.out.println("DEBUG: ValidationService.validateAndAddAttributes() called");
        System.out.println("DEBUG: requiredParameters: " + requiredParameters);
        System.out.println("DEBUG: DTO: " + dto);
        System.out.println();

        // fill DTO with validated data
        User userDTO = new User();
        User user = null;

        Set<String> keys = requiredParameters.keySet();
        System.out.println("DEBUG: Keys: " + keys);
        System.out.println("userName: " + requiredParameters.get("userName"));
        System.out.println("userPassword: " + requiredParameters.get("password"));
        System.out.println();

        for (String key : keys) {
            switch (key) {
                case "userName":
                    String userName = requiredParameters.get(key).toString();
                    validateName(userName);
                    user = wishService.getUserByUsername(userName);
                    if (user == null) {
                        user = new User();
                        user.setName(userName);
                    }
                    userDTO = user;
                    break;
                case "email":
                    String email = (String) requiredParameters.get(key);
                    System.out.println("debug case email");
                    System.out.println("- email: " + email);
                    System.out.println(" if-else");
                    System.out.println("- userDTO: " + dto.getUserDTO());
                    if (dto.getUserDTO().name == null) {
                        userDTO = wishService.getUserByEmail(email);
                        System.out.println("- userDTO: " + userDTO);
                    } else {
                        userDTO.setEmail(validateEmail((String) requiredParameters.get(key)));
                    }
                    break;
                case "password":
                    String password = (String) requiredParameters.get(key);
                    System.out.println("DEBUG: password");
                    System.out.println("validated password: " + validatePassword(password));
                    userDTO.setUserPassword(validatePassword(password));
                    System.out.println("DEBUG: password after validation: " + userDTO.getUserPassword());
                    break;
                case "url":
                    // check if item in userDTO is not null, if null create item and add url to it
                    if (dto.getItemDTO() == null) {
                        Item item = new Item();
                        item.setURL(validateURL((String) requiredParameters.get(key)));
                        dto.setItemDTO(item);
                    } else {
                        dto.getItemDTO().setURL(validateURL((String) requiredParameters.get(key)));
                    }
                    break;
                case "price":
                    if (dto.getItemDTO() == null) {
                        Item item = new Item();
                        item.setPrice(validatePrice((Double) requiredParameters.get(key)));
                        dto.setItemDTO(item);
                    } else {
                        dto.getItemDTO().setPrice(validatePrice((Double) requiredParameters.get(key)));
                    }
                    break;

                    // Model, RedirectAttributes, wishlistName and itemName are not validated but added to the DTO
                case "wishlistName":
                    String wishlistName = (String) requiredParameters.get(key);
                    validateName(wishlistName);

                    dto.setWishlistDTO(dto.getUserDTO().getWishlist(wishlistName)); // userDTO already initialized with all its wishlists from the DB, so getWishlist can find by searching the name
                    dto.setWishlistName(wishlistName);
                    break;
                case "itemName":
                    String itemName = (String) requiredParameters.get(key);
                    validateName(itemName);

                    dto.setItemName(itemName);
                    break;
                case "itemID":
                    int itemID = (int) requiredParameters.get(key);
                    validateNotNull(List.of(itemID, dto.getItemDTO()));

                    dto.setItemDTO(dto.getUserDTO().getItem(itemID));
                    break;
                case "model":
                    dto.setModel((Model) requiredParameters.get(key));
                    break;
                case "redirectAttributes":
                    dto.setRedirectAttributes((RedirectAttributes) requiredParameters.get(key));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid key: " + key);
            }
        }
        dto.setUserDTO(userDTO);
        System.out.println("DEBUG: DTO after validation: " + dto);
        System.out.println();
        return dto;
    }

    public boolean validateLogin(String email, String password) {

        User user = wishService.getUserByEmail(email);

        System.out.println("DEBUG validateLogin");
        System.out.println("getUserByEmail");
        System.out.println(" - user: "+ user);
        System.out.println("userFromDB");
        System.out.println(" - email: "+ user.getEmail());
        System.out.println(" - password: "+ user.getUserPassword());
        System.out.println("paraneters");
        System.out.println(" - email: "+ email);
        System.out.println(" - password: "+ password);

        if (user != null) {
            if (user.getUserPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

        // complex rule-check methods with combination of specific and individual rules



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


    public boolean validateNotNull(List<Object> objectList) {
        for (Object obj : objectList) {
            if (obj == null || (obj instanceof String && ((String) obj).isEmpty())) {
                throw new IllegalArgumentException("Parameters cannot be null or empty");
            }
            else if (obj instanceof Double && (Double)obj == 0) {
                throw new IllegalArgumentException("Price cannot be null or zero");
            }
        }
        return true;
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
        if (name == null) {
            return "";
        }
        return name.trim();
    }






}
