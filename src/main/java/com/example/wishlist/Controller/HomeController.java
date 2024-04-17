package com.example.wishlist.Controller;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
import com.example.wishlist.Service.ValidationService;
import com.example.wishlist.Service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
// frontend vscode
// backend intellij

@Controller
public class HomeController {

    @Autowired
    private WishService wishService;
    @Autowired
    private ValidationService validationService;

    @GetMapping("/home")
    public String home(Model model) {

        // model lists: Users, Wishlists, Items
        List<User> userList = wishService.getUsers();
        model.addAttribute("users", userList);

        return "home";
    }


    @GetMapping("/user/{userName}")
    public String userPage(@PathVariable("userName") String username, Model model) {
        try {
            // get User
            User user = wishService.getUserByUsername(username);

            // Check if User exists and retrieve Wishlists
            if(user != null) {
                List<Wishlist> wishlists = user.getWishlists();

                // Add user and wishlists to model
                model.addAttribute("user", user);
                model.addAttribute("wishlists", wishlists);
            } else {
                throw new Exception("User does not exist");
            }

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errorPage";
        }
        return "userPage";
    }

    @GetMapping("/{userName}/wishlist/{wishlistName}")
    public String wishlistPage(@PathVariable("userName") String userName,
                               @PathVariable("wishlistName") String wishlistName,
                               Model model) {
        try {
            // Get User and Wishlist
            User user = wishService.getUserByUsername(userName);
            Wishlist wishlist = null;
            if(user != null) {
                wishlist = wishService.getWishlistByName(wishlistName, user);
            } else {
                throw new Exception("User does not exist");
            }

            // add to Model if not null
            if(wishlist != null) {
                List<Item> items = wishService.getItemsFromWishlist(wishlist);

                model.addAttribute("user", user);
                model.addAttribute("items", items);
            } else {
                throw new Exception("Wishlist does not exist");
            }

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errorPage";
        }
        return "wishlistPage";
    }

    @PostMapping("/user")
    public String addUser(@RequestParam("userName") String userName, RedirectAttributes redirectAttributes) {
        List<Object> requiredParameters = Arrays.asList(userName);

        try {
            validationService.validateNotNullInput(requiredParameters);
            userName = validationService.validateName(userName); // validate the username
            wishService.addUser(userName);
            redirectAttributes.addFlashAttribute("success", "User '" + userName + "' added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage()); // Uses the exception message set by the ValidationService method
        }
        return "redirect:/home";
    }

    @PostMapping("/wishlist")
    public String addWishlist(
            @RequestParam("userName") String userName,
            @RequestParam("wishlistName") String wishlistName,
            RedirectAttributes redirectAttributes) {
        List<Object> requiredParameters = Arrays.asList(wishlistName);

        try {
            validationService.validateNotNullInput(requiredParameters);
            wishlistName = validationService.validateName(wishlistName); // only need to validate the user input: wishlistName
            wishService.addWishlist(userName, wishlistName);
            redirectAttributes.addFlashAttribute("success", "Wishlist '" + wishlistName + "' added successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage()); // Uses the exception message by with the ValidationService method
        }
        return "redirect:/user/" + userName;
    }

    @PostMapping("/item")
    public String addItem(@RequestParam("userName") String userName,
                          @RequestParam("wishlistName") String wishlistName,
                          @RequestParam("itemName") String itemName,
                          @RequestParam("price") Double price,
                          @RequestParam("URL") String URL,
                          RedirectAttributes redirectAttributes) {

        List<Object> requiredParameters = Arrays.asList(itemName, price, URL);

        try {
            validationService.validateNotNullInput(requiredParameters);
            // validate user inputs:
            itemName = validationService.validateName(itemName);
            price = validationService.validatePrice(price);
            URL = validationService.validateURL(URL);

            wishService.addItem(userName, wishlistName, itemName, price, URL);
            redirectAttributes.addFlashAttribute("success", "Item added successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage()); // Uses the exception message by with the ValidationService method
        }
        return "redirect:/" + userName + "/wishlist/" + wishlistName;
    }


}

