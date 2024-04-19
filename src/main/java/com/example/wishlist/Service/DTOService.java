package com.example.wishlist.Service;

import com.example.wishlist.Model.DTO;
import com.example.wishlist.Model.User;
import com.example.wishlist.Repository.WishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class DTOService {

    private final WishService wishService;
    private final WishRepo wishRepo;
    private final ValidationService validationService;

    @Autowired
    public DTOService(WishService wishService, WishRepo wishRepo, ValidationService validationService) {
        this.wishService = wishService;
        this.wishRepo = wishRepo;
        this.validationService = validationService;
    }


    public void logInUser(DTO dto, String email, String password, Model model, RedirectAttributes redirectAttributes) throws Exception {
        User loggedInUser = dto.getUserDTO(); // pass userDTO to loggedInUser
        dto.setUserDTO(new User()); // clear userDTO

        if(validationService.validateLogin(email, password)) {
            loggedInUser.setLoggedIn(true);
            dto.setLoggedInUser(loggedInUser);
            model.addAttribute("dto", dto);
            redirectAttributes.addFlashAttribute("success", "User '" + dto.getLoggedInUser().name + "' logged in successfully!");

        } else {
            throw new Exception("Invalid email or password");
        }
    }
    public void mergeUserDTOWithUserFromDB(DTO dto) {
        User userDTO = dto.getUserDTO();
        User userFromDB = wishRepo.getUser(userDTO.getName());

        for (String input : dto.getInputs()) {
            switch (input) {
                case "setName":
                    userFromDB.setName(userDTO.getName());
                    break;
                case "setUserPassword":
                    userFromDB.setUserPassword(userDTO.getUserPassword());
                    break;
                case "setEmail":
                    userFromDB.setEmail(userDTO.getEmail());
                    break;
                case "setWishlists":
                    userFromDB.setWishlists(userDTO.getWishlists());
                    break;
                case "setLoggedIn":
                    userFromDB.setLoggedIn(userDTO.isLoggedIn());
                    break;
                // continue as needed for all setter fields
                default:
                    break;
            }
        }

        wishRepo.addUser(userFromDB);
    }

}
