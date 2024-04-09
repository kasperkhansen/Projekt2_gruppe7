package com.example.wishlist.Controller;

import com.example.wishlist.Model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserController userController;

    @GetMapping("/home")
    public String home(Model model) {
        // model lists: Users, Wishlists, Items
        model.addAttribute("users", userController.getUsers());
        model.addAttribute("wishlists", userController.getWishlists());
        model.addAttribute("items", userController.getItems());

        return "startpage";
    }

    @GetMapping("/user/{userId}")
    public String userPage(@PathVariable("userId") int userId, Model model) {
        List<Wishlist> wishlists = userController.getWishlists(userId);
        model.addAttribute("wishlists", wishlists);

        return "userpage";
    }



}

