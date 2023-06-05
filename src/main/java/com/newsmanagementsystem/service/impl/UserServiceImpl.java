package com.newsmanagementsystem.service.impl;


import com.newsmanagementsystem.dto.requests.CreateUserRequest;
import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.mapper.UserMapper;
import com.newsmanagementsystem.model.MainEditor;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.repository.UserRepository;
import com.newsmanagementsystem.service.NewsService;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsService newsService;


    @Override
    public void create(MainEditor mainEditor) {
        userRepository.save(mainEditor);
    }

    @Override
    public ResponseEntity<HttpStatus> createPublicUser(CreateUserRequest createUserRequest) {
        User user = UserMapper.INSTANCE.createPublicUserRequest(createUserRequest);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<DisplayNewsResponse>> displayNews(Long userId) {

        boolean result = userRepository.findNonSubscriberUsers().stream().anyMatch(user->user.getId().equals(userId));
        if(result){
            return newsService.displayNewsForNonSubscriber();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> assignToPublisherEditor(Long userId) {

        try{
            userRepository.assignToPublisherEditor(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<HttpStatus> assignToSubscriber(Long userId) {
        try{
            userRepository.assignToSubscriber(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<User> findMainEditors() {
        return userRepository.findMainEditors();
    }

    @Override
    public List<User> findPublisherEditors() {
        return userRepository.findPublisherEditors();
    }

    @Override
    public List<User> findSubscriberUsers() {
        return userRepository.findSubscriberUsers();
    }

    @Override
    public List<User> findNonSubscriberUsers() {
        return userRepository.findNonSubscriberUsers();
    }
}
