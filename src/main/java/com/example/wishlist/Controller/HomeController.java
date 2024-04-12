package com.example.wishlist.Controller;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
import com.example.wishlist.Service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private WishService wishService;

    @GetMapping("/home")
    public String home(Model model) {

        // model lists: Users, Wishlists, Items
        List<User> userList = wishService.getUsers();
        model.addAttribute("users", userList);

        return "startpage";
    }


    @GetMapping("/user/{userName}")
    public String userPage(@PathVariable("userName") String username, Model model) {

        // get User
        User user = wishService.getUserByUsername(username);

        // check if User is not null and retrieve Wishlists
        if(user != null){
            System.out.println(" User:"+user);
            System.out.println(" User id: " + user.getUserID());

            List<Wishlist> wishlists = user.getWishlists();
            System.out.println("- wishlists from User object "+ user.getWishlists());

            // add user id and wishlists to model
            model.addAttribute("user", user);
            model.addAttribute("userId", user.getUserID());
            model.addAttribute("wishlists", wishlists);


        }

        return "userpage";
    }

    @PostMapping("/user")
    public String addUser(@RequestParam("userName") String userName) {

        // wishService addUser - calls database
        wishService.addUser(userName);

        //redirecting to the same user page after adding the user
        return "redirect:/home";
    }



    @GetMapping("/wishlist/{wishlistName}")
    public String wishlistPage(@PathVariable("wishlistName") String wishlistName, @RequestParam("userName") String userName, Model model) {

        // get wishlist and User
        User user = wishService.getUserByUsername(userName);
        Wishlist wishlist = wishService.getWishlistByName(wishlistName, user);

        if(user != null && wishlist != null){
            // wishService getItems - calls hardcoded data
            List<Item> items = wishService.getItemsFromWishlist(wishlistName);

            // add user and items to model
            model.addAttribute("items", items);
            model.addAttribute("user", user);
        }

        return "wishlistpage";
    }

    @PostMapping("/wishlist")
    public String addWishlist(@RequestParam("username") String username, @RequestParam("wishlist_name") String wishlist_name) {

        // wishService addWishlist - calls database
        wishService.addWishlist(username, wishlist_name);

        //redirecting to the same user page after adding the wishlist
        return "redirect:/user/" + username;
    }


}

