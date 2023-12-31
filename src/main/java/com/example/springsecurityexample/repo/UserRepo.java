package com.example.springsecurityexample.repo;

import com.example.springsecurityexample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
