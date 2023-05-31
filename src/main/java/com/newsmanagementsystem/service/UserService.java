package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.CreatePublicUserRequest;
import com.newsmanagementsystem.model.MainEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface UserService{
     void create(MainEditor mainEditor); //??

     ResponseEntity<HttpStatus> createPublicUser(CreatePublicUserRequest createPublicUserRequest);

}
