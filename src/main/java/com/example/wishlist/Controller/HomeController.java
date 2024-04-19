package com.example.wishlist.Controller;

import com.example.wishlist.Model.DTO;
import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
import com.example.wishlist.Service.ValidationService;
import com.example.wishlist.Service.WishService;
import com.example.wishlist.Service.DTOService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
// import http session

import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.SessionAttributes;



// frontend vscode
// backend intellij

@Controller
@SessionAttributes("DTO")
public class HomeController {

    @Autowired
    private WishService wishService;
    @Autowired
    private DTOService DTOService;
    @Autowired
    private ValidationService validationService;

    @GetMapping("/home")
    public String home(@ModelAttribute("DTO") DTO dto, Model model, RedirectAttributes redirectAttributes, SessionStatus status) {

        model.addAttribute("users", wishService.getUsers());

        dto.setCurrentPage("/home");

        model.addAttribute("DTO", dto);


        return "home";
    }

    @GetMapping("/login")
    public String login(Model model, RedirectAttributes redirectAttributes) {

        DTO dto = new DTO();  // Creation of Session => new DTO object

        dto.setCurrentPage("/login");

        model.addAttribute("DTO", dto);
        return "loginpage";
    }


    @GetMapping("/user/{userName}")
    public String userpage(@ModelAttribute("DTO") DTO dto, @PathVariable("userName") String username, Model model) {
        try {
            System.out.println("DEBUG userpage");
            System.out.println("DTO: " + dto);
            System.out.println();
            if(dto.getUserDTO() != null) {
                dto.setCurrentPage("/user/" + username);
                dto.setFriends(wishService.getFriends(dto.getLoggedInUser()));

                model.addAttribute("DTO", dto);
                model.addAttribute("users", wishService.getUsers());
                model.addAttribute("friends", dto.getFriends()); // friends of the logged in user

                return "userpage";
            } else {
                throw new Exception("User does not exist");
            }

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{userName}/wishlist/{wishlistName}")
    public String wishlistpage(@ModelAttribute("DTO") DTO dto,
                               @PathVariable("userName") String userName,
                               @PathVariable("wishlistName") String wishlistName,
                               Model model) {
        Map<String, Object> requiredParameters = new HashMap<>(Map.of("userName", userName, "wishlistName", wishlistName, "model", model));

        try {
            dto = validationService.validateAndAddAttributes(requiredParameters, dto);
            dto.setCurrentPage(userName + "/wishlist/" + wishlistName);
            model.addAttribute("DTO", dto);

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errorPage";
        }
        return "wishlistpage";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("DTO") DTO dto,
                          @RequestParam("userName") String userName,
                          @RequestParam("email") String email,
                          @RequestParam("password") String password,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        Map<String, Object> requiredParameters = new HashMap<>(Map.of("userName", userName, "email", email, "password", password));

        try {
            dto = validationService.validateAndAddAttributes(requiredParameters, dto);

            wishService.addUser(dto);

            model.addAttribute("DTO", dto);
            redirectAttributes.addFlashAttribute("DTO", dto);
            redirectAttributes.addFlashAttribute("success", "User '" + userName + "' added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage()); // Uses the exception message set by the ValidationService method
        }
        return "redirect:/home";
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("DTO") DTO dto,
                          @RequestParam("userName") String userName,
                          @RequestParam("email") String email,
                          @RequestParam("password") String password,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        Map<String, Object> requiredParameters = new HashMap<>(Map.of("userName", userName, "email", email, "password", password));

        try {
            dto = validationService.validateAndAddAttributes(requiredParameters, dto);

            System.out.println("DEBUG registerUser");
            System.out.println("DTO: " + dto);
            System.out.println("userDTO: " + dto.getUserDTO());
            wishService.addUser(dto);

            model.addAttribute("DTO", dto);
            redirectAttributes.addFlashAttribute("DTO", dto);
            redirectAttributes.addFlashAttribute("success", "User '" + userName + "' added successfully!");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/login";
    }

    @PostMapping("/loginUser")
    public String loginUser (@ModelAttribute("DTO") DTO dto,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             HttpSession session
    ) {
        Map<String, Object> requiredParameters = new HashMap<>(Map.of("email", email, "password", password));

        try {
            System.out.println("DEBUG loginUser");
            System.out.println("- requiredParameters: " + requiredParameters);
            System.out.println();
            dto = validationService.validateAndAddAttributes(requiredParameters, dto);

            DTOService.logInUser(dto, email, password, model, redirectAttributes);


            System.out.println("loggedInUser: " + dto.getLoggedInUser());
            System.out.println("userDTO: " + dto.getUserDTO());
            System.out.println();
            System.out.println();
            model.addAttribute("userName", wishService.getUsers());

            session.setAttribute("DTO", dto);
            model.addAttribute("DTO", dto);
            return "redirect:/user/" + dto.getLoggedInUser().getName();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage()); // Uses the exception message set by the ValidationService method
            return "redirect:/login";
        }
    }



    @PostMapping("/addfriend")
    public String addFriend(@ModelAttribute("DTO") DTO dto,
                            @RequestParam("userName") String userName,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password,
                            HttpSession session,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        Map<String, Object> requiredParameters = new HashMap<>(Map.of("userName", userName, "email", email, "password", password, "model", model, "redirectAttributes", redirectAttributes));

        try {
            dto = validationService.validateAndAddAttributes(requiredParameters, dto);

            wishService.addFriend(dto);

            session.setAttribute("DTO", dto);
            model.addAttribute("DTO", dto);
            redirectAttributes.addFlashAttribute("success", "User '" + userName + "' added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage()); // Uses the exception message set by the ValidationService method
        }
        return "redirect:" + dto.getCurrentPage();
    }



    @PostMapping("/addWishlist")
    public String addWishlist(@ModelAttribute("DTO") DTO dto,
                              @RequestParam("wishlistName") String wishlistName,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        Map<String, Object> requiredParameters = new HashMap<>(Map.of("wishlistName", wishlistName, "model", model, "redirectAttributes", redirectAttributes));
        String loggedInUserName = dto.getLoggedInUser().getName();
        try {
            dto = validationService.validateAndAddAttributes(requiredParameters, dto);
            wishService.addWishlist(loggedInUserName, wishlistName);

            session.setAttribute("DTO", dto);
            model.addAttribute("DTO", dto);
            redirectAttributes.addFlashAttribute("success", "Wishlist '" + wishlistName + "' added successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage()); // Uses the exception message by with the ValidationService method
        }

        return "redirect:/user/" + dto.getCurrentPage();
    }

    @PostMapping("/addItem")
    public String addItem(@ModelAttribute("DTO") DTO dto,
                          @RequestParam("userName") String userName,
                          @RequestParam("wishlistName") String wishlistName,
                          @RequestParam("itemName") String itemName,
                          @RequestParam("price") Double price,
                          @RequestParam("URL") String URL,
                          HttpSession session,
                            Model model,
                          RedirectAttributes redirectAttributes) {

        Map<String, Object> requiredParameters = new HashMap<>(Map.of("userName", userName, "wishlistName", wishlistName, "itemName", itemName, "price", price, "URL", URL, "model", model, "redirectAttributes", redirectAttributes));

        try {
            dto = validationService.validateAndAddAttributes(requiredParameters, dto);


            wishService.addItem(userName, wishlistName, itemName, price, URL);
            session.setAttribute("DTO", dto);
            model.addAttribute("DTO", dto);
            redirectAttributes.addFlashAttribute("success", "Item added successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage()); // Uses the exception message by with the ValidationService method
        }
        return "redirect:/" +dto.getCurrentPage();
    }

    @PostMapping("/reserveItem")
    public String reserveItem(@ModelAttribute("DTO") DTO dto,
                              @RequestParam("loggedInUser") User loggedInUser,
                              @RequestParam("userName") String userName,
                              @RequestParam("wishlistName") String wishlistName,
                              @RequestParam("itemName") String itemName,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        Map<String, Object> requiredParameters = new HashMap<>(Map.of("loggedInUser", loggedInUser, "userName", userName, "wishlistName", wishlistName, "itemName", itemName, "model", model, "redirectAttributes", redirectAttributes));

        try {
            dto = validationService.validateAndAddAttributes(requiredParameters, dto);

            // initialize with items retrieved from the database
            dto.setWishlist(wishService.getWishlistByName(wishlistName, wishService.getUserByUsername(userName)));

            // main functinality
            wishService.toggleReserveItem(dto);

            session.setAttribute("DTO", dto);
            model.addAttribute("DTO", dto);

            redirectAttributes.addFlashAttribute("success", "Item '"+itemName+"'reserved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/" + dto.getCurrentPage();
    }

    @PostMapping("/deleteItem")
    public String deleteItem(@ModelAttribute("DTO") DTO dto,
                             @RequestParam("itemID") int itemID,
                             HttpSession session,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        try {
            dto = validationService.validateAndAddAttributes(Map.of("itemID", itemID), dto);

            wishService.deleteItem(dto);
            session.setAttribute("DTO", dto);
            model.addAttribute("DTO", dto);
            redirectAttributes.addFlashAttribute("success", "Item '"+dto.getItemDTO()+"' deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/" + dto.getCurrentPage();
    }

}

