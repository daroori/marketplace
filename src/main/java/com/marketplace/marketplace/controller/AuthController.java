package com.marketplace.marketplace.controller;

import com.marketplace.marketplace.config.JwtUtil;
import com.marketplace.marketplace.model.User;
import com.marketplace.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
            );
        } catch (AuthenticationException e){
            return ResponseEntity.badRequest().body("Incorrect Username or Password");
        }

        final Optional<User> userDetails = userService.findByUsername(user.getUsername());
        if(userDetails.isPresent()){
            final String jwt = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(jwt);
        }
        else{
            return ResponseEntity.badRequest().body("User not found");
        }
    }
}
