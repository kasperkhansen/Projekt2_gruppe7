package com.example.wishlist.Controller;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserController {

    public List<User> getUsers() {
        return new ArrayList<>((List.of(
                new User(1, "User1", "1234", "1@mail.dk"),
                new User(2, "User2", "1234", "2@mail.dk")
        )));
    }

    public List<Wishlist> getWishlists() {
        return new ArrayList<>((List.of(
                new Wishlist(1, 1, "Wishlist1", getItems()),
                new Wishlist(2, 1, "Wishlist2", getItems()),
                new Wishlist(3, 1, "Wishlist3", getItems())
        )));
    }

    public List<Item> getItems() {
        return new ArrayList<>((List.of(
                new Item(1, "Item1", 100.0, "www.item1.dk"),
                new Item(2, "Item2", 200.0, "www.item2.dk"),
                new Item(3, "Item3", 300.0, "www.item3.dk")
        )));
    }
    public List<Wishlist> getWishlists(int userID) {
        return getWishlists();
    }
    public List<Item> getItems(int wishlistID) {
        return getItems();
    }
}
