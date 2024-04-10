package com.example.wishlist.Repository;

import com.example.wishlist.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    // Here you can define custom queries, if needed






}
