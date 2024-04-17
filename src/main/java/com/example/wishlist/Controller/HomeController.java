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

import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

        // get User
        User user = wishService.getUserByUsername(username);

        // check if User is not null and retrieve Wishlists
        if(user != null){
            System.out.println(" User:"+user);
            System.out.println(" User id: " + user.getID());

            List<Wishlist> wishlists = user.getWishlists();
            System.out.println("- wishlists from User object "+ user.getWishlists());

            // add user id and wishlists to model
            model.addAttribute("user", user);
            model.addAttribute("userId", user.getID());
            model.addAttribute("wishlists", wishlists);
        }

        return "userpage";
    }

    @GetMapping("/{userName}/wishlist/{wishlistName}")
    public String wishlistPage(@PathVariable("userName") String userName, @PathVariable("wishlistName") String wishlistName, Model model) {

        // get wishlist and User
        User user = wishService.getUserByUsername(userName);
        Wishlist wishlist = wishService.getWishlistByName(wishlistName, user);

        if (user != null && wishlist != null){
            // wishService getItems - calls hardcoded data
            List<Item> items = wishService.getItemsFromWishlist(wishlist);

            // add user and items to model
            model.addAttribute("items", items);
            model.addAttribute("user", user);
        }

        return "wishlistpage";
    }

    @PostMapping("/user")
    public String addUser(@RequestParam("userName") String userName, RedirectAttributes redirectAttributes) {
        try {
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
        try {
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
        try {
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

