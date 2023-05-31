package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.requests.CreatePublicUserRequest;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class PublicUserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<HttpStatus> create(CreatePublicUserRequest createPublicUserRequest){
        return userService.createPublicUser(createPublicUserRequest);
    }
}
