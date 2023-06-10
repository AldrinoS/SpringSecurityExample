package com.example.springsecurityexample.controller;

import com.example.springsecurityexample.config.JwtUtility;
import com.example.springsecurityexample.model.JwtRequest;
import com.example.springsecurityexample.model.JwtResponse;
import com.example.springsecurityexample.model.User;
import com.example.springsecurityexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hello")
public class HelloRestController {

    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    AuthenticationManager authenticationManager;

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

    @PostMapping("auth")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid Credentials");
        }

        final UserDetails userDetails = userService.loadUserByUsername(request.getUsername());

        final String token = jwtUtility.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }
}

