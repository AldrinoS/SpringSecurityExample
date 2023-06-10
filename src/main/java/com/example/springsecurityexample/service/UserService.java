package com.example.springsecurityexample.service;

import com.example.springsecurityexample.model.User;
import com.example.springsecurityexample.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    public void add(User user) throws Exception {

        Optional<User> optionalUser = userRepo.findByUsername(user.getUsername());
        if(optionalUser.isPresent()) {
            throw new Exception("User Existing");
        }

        userRepo.save(user);
    }

    public List<User> show() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepo.findByUsername(username);
        return optionalUser.map(user -> new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>()))
                .orElseThrow(()->new UsernameNotFoundException(username + " user not found!!"));

    }
}
