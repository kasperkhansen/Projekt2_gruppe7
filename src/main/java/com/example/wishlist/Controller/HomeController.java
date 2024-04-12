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
    public String userPage(@PathVariable("userName") String userName, Model model) {

        // get Users and Wishlists
        User user = wishService.getUserByUsername(userName);
        List<Wishlist> wishlists = wishService.getWishlistsFrom(userName);

        // add user id to model
        if(user != null){
            model.addAttribute("userId", user.getId());
        }
        model.addAttribute("wishlists", wishlists);

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
    public String wishlistPage(@PathVariable("wishlistName") String wishlistName, Model model) {

        // wishService getItems - calls hardcoded data
        List<Item> items = wishService.getItemsFromWishlist(wishlistName);
        model.addAttribute("items", items);

        return "wishlistpage";
    }

    @PostMapping("/wishlist")
    public String addWishlist(@RequestParam("user") int userId, @RequestParam("wishlistName") String wishlistName) {

        // wishService addWishlist - calls database
        wishService.addWishlist(userId, wishlistName);

        //redirecting to the same user page after adding the wishlist
        return "redirect:/user/" + userId;
    }


}

