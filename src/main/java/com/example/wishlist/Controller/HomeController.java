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
        model.addAttribute("wishlists", wishService.getWishlists());
        model.addAttribute("items", wishService.getItems());


        return "startpage";
    }

    @GetMapping("/user/{userId}")
    public String userPage(@PathVariable("userId") int userId, Model model) {

        // wishService getWishlist - calls hardcoded data
        List<Wishlist> wishlists = wishService.getWishlists(userId);
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



    @GetMapping("/wishlist/{wishlistId}")
    public String wishlistPage(@PathVariable("wishlistId") int wishlistId, Model model) {

        // wishService getItems - calls hardcoded data
        List<Item> items = wishService.getItems(wishlistId);
        model.addAttribute("items", items);

        return "wishlistpage";
    }

    @PostMapping("/wishlist")
    public String addWishlist(@RequestParam("user") int userId, @RequestParam("wishlistName") String wishlistName) {

        // wishService addWishlist - calls database
        wishService.addWishlist(wishlistName, userId);

        //redirecting to the same user page after adding the wishlist
        return "redirect:/user/" + userId;
    }


}

