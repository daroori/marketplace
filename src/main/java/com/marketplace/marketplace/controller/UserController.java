package com.marketplace.marketplace.controller;

import com.marketplace.marketplace.model.User;
import com.marketplace.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public ResponseEntity<User> registerUser(@RequestBody User user){
       User registeredUser = userService.registerUser(user);
       return ResponseEntity.ok(registeredUser);
    }
}
