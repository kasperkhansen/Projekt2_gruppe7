package com.example.wishlist.Controller;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserController {


    List<User> users = new ArrayList<>((List.of(
            new User(1, "Jørgen", "1234", "1@mail.dk", getWishlistsJorgen()),
            new User(2, "Alma", "1234", "2@mail.dk", getWishlistsAlma())
    )));

    private List<Wishlist> getWishlistsAlma() {
        return (List.of(
                new Wishlist(1, 1, "Birthday", getItems()),
                new Wishlist(2, 1, "Christmas", getItems()),
                new Wishlist(3, 1, "Apartment", getItems())
        ));
    }

    private List<Wishlist> getWishlistsJorgen() {
        return (List.of(
                new Wishlist(1,1, "Holiday", getItems()),
                new Wishlist(2, 1, "Christmas", getItems()),
                new Wishlist(3, 1, "Apartment", getItems())
        ));
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Wishlist> getWishlists() {
        List<Wishlist> allWishlists = new ArrayList<>();
        allWishlists.addAll(getWishlistsAlma());
        allWishlists.addAll(getWishlistsJorgen());

        return allWishlists;
    }
    public List<Wishlist> getWishlist(String userName) {
        for (User user : users) {
            if(user.getName().equals(userName)) {
                return user.getWishlists();
            }
        }
        return null;
    }

    public List<Item> getItems() {
        return new ArrayList<>((List.of(
                new Item(1, "Milk", 100.0, "www.item1.dk"),
                new Item(2, "Library", 200.0, "www.item2.dk"),
                new Item(3, "Alpaca", 300.0, "www.item3.dk")
        )));
    }
    public List<Wishlist> getWishlists(int userID) {
        for (User user : users) {
            if (user.getId()==userID) {
                return user.getWishlists();
            }
        }
        return null;
    }
    public List<Item> getItems(int wishlistID) {
        return getItems();
    }
}
