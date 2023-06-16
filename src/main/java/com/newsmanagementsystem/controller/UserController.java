package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.requests.CreateUserRequest;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    public ResponseEntity<HttpStatus> create(CreateUserRequest createUserRequest){
        return userService.createPublicUser(createUserRequest);
    }
}
