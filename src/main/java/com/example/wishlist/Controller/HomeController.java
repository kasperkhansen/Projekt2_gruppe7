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

    @PostMapping("/user")
    public String addUser(@RequestParam("userName") String userName) {

        // wishService addUser - calls database
        wishService.addUser(userName);

        //redirecting to the same user page after adding the user
        return "redirect:/home";
    }



    @GetMapping("/{userName}/wishlist/{wishlistName}")
    public String wishlistPage(@PathVariable("userName") String userName, @PathVariable("wishlistName") String wishlistName, Model model) {

        // get wishlist and User
        User user = wishService.getUserByUsername(userName);
        Wishlist wishlist = wishService.getWishlistByName(wishlistName, user);

        if(user != null && wishlist != null){
            // wishService getItems - calls hardcoded data
            List<Item> items = wishService.getItemsFromWishlist(wishlist);

            // add user and items to model
            model.addAttribute("items", items);
            model.addAttribute("user", user);
        }

        return "wishlistpage";
    }

    @PostMapping("/wishlist")
    public String addWishlist(@RequestParam("username") String username, @RequestParam("wishlist_name") String wishlist_name) {

        // debug addWishlist POST
        System.out.println("DEBUG addWishlist POST method");
        System.out.println("name: " + username);
        System.out.println("wishlist_name: " + wishlist_name);
        System.out.println();

        // wishService addWishlist - calls database
        wishService.addWishlist(username, wishlist_name);

        //redirecting to the same user page after adding the wishlist
        return "redirect:/user/" + username;
    }

    @PostMapping("/item")
    public String addItem(@RequestParam("username") String username,
                          @RequestParam("wishlist_name") String wishlist_name,
                          @RequestParam("itemName") String item_name,
                          @RequestParam("price") Double item_price,
                          @RequestParam("URL") String item_url,
                          Model model) {

        // You will need to add a new item to user's specific wishlist here
        wishService.addItem(username, wishlist_name, item_name, item_price, item_url);

        // After adding the item, redirect back to the wishlist page.
        return "redirect:/" + username + "/wishlist/" + wishlist_name;
    }


}

