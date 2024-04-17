package com.example.wishlist.Controller;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
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
            wishService.addUser(userName);
            redirectAttributes.addFlashAttribute("success", "User added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add user");
        }
        return "redirect:/home";
    }



    @PostMapping("/wishlist")
    public String addWishlist(
            @RequestParam("userName") String userName,
            @RequestParam("wishlistName") String wishlistName,
            RedirectAttributes redirectAttributes) {
        // Validate wishlistName doesn't include special characters like æ, ø, å
        if (!wishlistName.matches("[a-zA-Z0-9 ]+")) {
            redirectAttributes.addFlashAttribute("error", "Invalid characters in the wishlist name");
        } else {
            try {
                wishService.addWishlist(userName, wishlistName);
                redirectAttributes.addFlashAttribute("success", "Wishlist added successfully");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Failed to add wishlist");
            }
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
            wishService.addItem(userName, wishlistName, itemName, price, URL);
            redirectAttributes.addFlashAttribute("success", "Item added successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add item");
        }
        return "redirect:/" + userName + "/wishlist/" + wishlistName;
    }


}

