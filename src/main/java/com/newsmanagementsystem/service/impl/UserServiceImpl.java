package com.newsmanagementsystem.service.impl;


import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.CreatePublicUserRequest;
import com.newsmanagementsystem.mapper.UserMapper;
import com.newsmanagementsystem.model.MainEditor;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.repository.UserRepository;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void create(MainEditor mainEditor) {
        userRepository.save(mainEditor);
    }

    @Override
    public ResponseEntity<HttpStatus> createPublicUser(CreatePublicUserRequest createPublicUserRequest) {
        User user = UserMapper.INSTANCE.createPublicUserRequest(createPublicUserRequest);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
