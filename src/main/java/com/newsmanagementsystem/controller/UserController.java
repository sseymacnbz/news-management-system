package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.requests.CreateUserRequest;
import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<HttpStatus> create(CreateUserRequest createUserRequest){
        return userService.createPublicUser(createUserRequest);
    }

    @GetMapping(value = "/news/{userId}")
    public ResponseEntity<List<DisplayNewsResponse>> displayNews(@PathVariable Long userId){
        return userService.displayNews(userId);
    }
}
