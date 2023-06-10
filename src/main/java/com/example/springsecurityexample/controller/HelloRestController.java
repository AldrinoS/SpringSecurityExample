package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.model.User;
import com.example.springsecurityexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hello")
public class HelloRestController {

    @Autowired
    UserService userService;

    @GetMapping("user")
    public String helloUser() {
        return "Hello User";
    }

    @GetMapping("admin")
    public String helloAdmin() {
        return "Hello Admin";
    }

    @PostMapping("add-user")
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        try {
            userService.add(user);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("User Added");
    }

    @GetMapping("show-user")
    public ResponseEntity<?> showAllUsers() {
        return ResponseEntity.ok(userService.show());
    }
}

