package com.example.wishlist.Service;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
import com.example.wishlist.Repository.WishRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WishService {

    @Autowired
    private WishRepo repo;


    // ------------------- CRUD Methods for Users, Wishlist and Items
    public void addUser(String username){
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

    public List<Wishlist> getWishlists() {
        return repo.fetchAllWishlists();
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







    // --------------------------------------------------------------

    public WishRepo getRepo() {
        return this.repo;
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


    /*
    // ------------------- Testing methods -------------------------
    public List<User> users = new ArrayList<>((List.of(
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



    public List<Wishlist> getWishlistsTest() {
        List<Wishlist> allWishlists = new ArrayList<>();
        allWishlists.addAll(getWishlistsAlma());
        allWishlists.addAll(getWishlistsJorgen());

        return allWishlists;
    }
    public List<Wishlist> getWishlistTest(String userName) {
        for (User user : users) {
            if(user.getItemName().equals(userName)) {
                return user.getWishlists();
            }
        }
        return null;
    }

    public List<Wishlist> getWishlistsTest(int userID) {
        for (User user : users) {
            if (user.getId()==userID) {
                return user.getWishlists();
            }
        }
        return null;
    }

    public void addItem(Wishlist wl, Item i){
        repo.addItem(wl,i);
    }


    public void updateWishlist(Wishlist wl){
        repo.updateWishlist(wl);
    }

     */

}
