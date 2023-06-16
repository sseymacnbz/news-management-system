package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import io.swagger.v3.oas.annotations.Parameter;
import com.newsmanagementsystem.dto.requests.CreateUserRequest;
import com.newsmanagementsystem.service.UserService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/news")
    @PageableAsQueryParam
    public ResponseEntity<Page<DisplayNewsResponse>> displayNews(@RequestParam Long userId, @Parameter(hidden = true) Pageable pageable){
        return userService.displayNews(userId, pageable);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<HttpStatus> create(CreateUserRequest createUserRequest){
        return userService.createPublicUser(createUserRequest);
    }
}
