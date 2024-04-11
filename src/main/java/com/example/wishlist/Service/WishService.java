package com.example.wishlist.Service;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Model.User;
import com.example.wishlist.Model.Wishlist;
import com.example.wishlist.Repository.WishRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishService {

    @Autowired
    private WishRepo repo;


    // ------------------- CRUD Methods for Users, Wishlist and Items
    public void addUser(String userName){
        User u = new User(userName);
        repo.addUser(u);
    }

    public void addWishlist(String wishlistName, int userId) {
        Wishlist wl = new Wishlist(wishlistName);
        User u = new User(userId);
        if (repo.checkUserExists(u) == true) {
            repo.addWishlist(wl, u);
        } else {
            repo.addUser(u);
            repo.addWishlist(wl, u);
        }
    }

    public void addItem(String itemName, double price, String url, String wishlistName) {
        Item i = new Item(itemName, price, url);
        Wishlist wl = new Wishlist(wishlistName);
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
        User u = new User(userName);
        return repo.fetchAllWishlistsFrom(u);
    }

    public List<Item> getItems(){
        return repo.fetchAllItems();
    }

    public List<Item> getItemsFromWishlist(String wishlistName) {
        Wishlist wl = new Wishlist(wishlistName);
        return repo.fetchAllItemsFrom(wl);
    }


    // -------------------- check methods
    public boolean checkUserExists(User u){
        return repo.checkUserExists(u);
    }







    // --------------------------------------------------------------

    public WishRepo getRepo() {
        return this.repo;
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
            if(user.getName().equals(userName)) {
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
