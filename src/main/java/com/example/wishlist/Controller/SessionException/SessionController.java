package com.example.wishlist.Controller.SessionException;

import com.example.wishlist.Model.DTO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@ControllerAdvice
public class SessionController {

    @ModelAttribute
    public void defaultSessionAttributes(@ModelAttribute("DTO") DTO dto, RedirectAttributes redirectAttributes) {
        if (dto.getCurrentPage() == null || dto.getCurrentPage().isEmpty()) {
            throw new MissingSessionAttributeException("DTO");
        }
    }
}
