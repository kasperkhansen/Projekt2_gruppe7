package com.example.wishlist.Repository;

import com.example.wishlist.Model.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepo {
    @Autowired
    JdbcTemplate template;

    public List<Items> fetchAll (){
        String sql = "SELECT * FROM ";
        return null;
    }

    public void addItem(Items i){
        String sql = "INSERT INTO Items ()";
        template.update(sql, i.getName(), i.getPrice() );
    }

}
