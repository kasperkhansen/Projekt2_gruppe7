package com.example.wishlist.Service;

import com.example.wishlist.Model.Item;
import com.example.wishlist.Repository.WishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    @Autowired
    WishRepo wishRepo;

    public List<Item> fetchAll(){
        return wishRepo.fetchAll();
    }

    public void addItem(Item i){
        wishRepo.addItem(i);
    }

}
