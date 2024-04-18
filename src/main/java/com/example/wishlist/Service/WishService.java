package com.example.wishlist.Service;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
import com.example.wishlist.Repository.WishRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class WishService {

    @Autowired
    private WishRepo repo;



    // ------------------- main functionality methods -------------------
        // reserve item
    public void reserveItem(String userNameOfTheUserReserving, Wishlist wishlist, String itemName) {
        System.out.println("DEBUG reserveItem");
        User user = getUserByUsername(userNameOfTheUserReserving);

        System.out.println("User: " + user);
        System.out.println("Wishlist: " + wishlist);
        System.out.println("Item: "+ wishlist.getItem(itemName));

        repo.reserveItem(user, wishlist.getItem(itemName));
    }
        // unreserve item
    public void unreserveItem(Wishlist wishlist, String itemName) {

        repo.unreserveItem(wishlist.getItem(itemName));
    }

    // ------------------- CRUD Methods for Users, Wishlist and Items
    public void addUser(String username, String email, String password){
        try {
            // check if userName is null
            if (username == null) {
                return;
            }

            // check if user exists in database
            if (repo.checkUserExists(new User(username)) == true) {
                return;
            }

            User u = new User(username);
            repo.addUser(u);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void addWishlist(String username, String wishlistName) {
        Wishlist wl = new Wishlist(wishlistName);
        User u = repo.getUser(username);

        System.out.println("DEBUG addWishlist");
        System.out.println("User: " + u);
        System.out.println("Wishlist: " + wl);

        if (repo.checkUserExists(u) == true) {
            // user exists -> add wishlist
            repo.addWishlist(wl, u);
        } else {

            // does our user have a name in the database
            if (username != null && !username.isEmpty()) {
                // Posible to insert a frontend comment
                System.out.println("The user does not exist in the repository");
            } else {
                repo.addUser(new User(username));
                repo.addWishlist(wl, u);
            }
        }
    }

    public void addItem(String username, String wishlistName, String itemName, double price, String url) {
        Item i = new Item(itemName, price, url);
        Wishlist wl = getWishlistByName(wishlistName, getUserByUsername(username));
        repo.addItem(wl, i);
    }


    // ------------------- Read Methods
    public List<User> getUsers() {
        return repo.fetchAllUsers();
    }

    public List<Wishlist> getWishlistsFrom(String userName) {
        User u = getUserByUsername(userName);
        if (u != null) {
            return repo.fetchAllWishlistsFrom(u);
        } else {
            return Collections.emptyList();
        }
    }

    public List<Item> getItems(){
        return repo.fetchAllItems();
    }

    public List<Item> getItemsFromWishlist(Wishlist wishlist) {
        Wishlist wl = wishlist;
        List<Item> items = repo.fetchAllItemsFrom(wl);
        System.out.println("DEBUG getItemsFromWishlist");
        System.out.println("Items: " + items);
        return items;
    }


    // -------------------- check methods
    public boolean checkUserExists(User u){
        return repo.checkUserExists(u);
    }

    public boolean checkWishlistExists(Wishlist wl){
        return repo.checkWishlistExists(wl);
    }

    // --------------------------------------------------------------

    public WishRepo getRepo() {
        return this.repo;
    }





    public User getUserByEmail(String email) {

        for (User user : getUsers()) {
            if (user.getEmail().equals(email) && user.getEmail() != null) {
                return user;
            }
        }

        return null;
    }


    public User getUserByUsername(String userName) {

        for (User user : getUsers()) {
            if (user.getName().equals(userName) && user.getName() != null) {
                return user;
            }
        }

        return null;
    }

    public Wishlist getWishlistByName(String wishlistName, User user) {

        for (Wishlist wl : getWishlistsFrom(user.getName())) {
            if (wl.getName().equals(wishlistName)) {
                return wl;
            }
        }

        return null;
    }


}
