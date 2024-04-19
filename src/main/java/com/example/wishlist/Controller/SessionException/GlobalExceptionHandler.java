package com.example.wishlist.Controller.SessionException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingSessionAttributeException.class)
    public String handleMissingSessionAttr(Exception ex) {
        return "redirect:/login";
    }
}
