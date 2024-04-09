package com.example.wishlist.Controller;

import com.example.wishlist.Model.Wishlist;
import com.example.wishlist.Service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class asd {
    @Autowired
    WishService wishService;

    @GetMapping("/")
    public String index(){
    }

}
