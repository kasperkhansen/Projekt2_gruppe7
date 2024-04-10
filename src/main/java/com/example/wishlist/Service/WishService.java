package com.example.wishlist.Service;

import com.example.wishlist.Model.Items;
import com.example.wishlist.Repository.WishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    @Autowired
    WishRepo wishRepo;

    public List<Items> fetchAll(){
        return wishRepo.fetchAll();
    }

    public void addItem(Items i){
        wishRepo.addItem(i);
    }

}
