package com.example.wishlist.Controller.SessionException;

public class MissingSessionAttributeException extends RuntimeException {
    public MissingSessionAttributeException(String message) {
        super(message);
    }
}
