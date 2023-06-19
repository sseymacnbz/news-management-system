package com.newsmanagementsystem.service.impl;


import com.newsmanagementsystem.dto.requests.CreateUserRequest;
import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.UserNotFoundException;
import com.newsmanagementsystem.mapper.UserMapper;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.repository.UserRepository;
import com.newsmanagementsystem.service.NewsService;
import com.newsmanagementsystem.service.UserService;
import com.newsmanagementsystem.utilities.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NewsService newsService;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseEntity<HttpStatus> createPublicUser(CreateUserRequest createUserRequest) {
        User user = UserMapper.INSTANCE.createPublicUserRequest(createUserRequest);
        userRepository.save(user);
        log.info(LogUtil.getMessage(Thread.currentThread().getStackTrace()[1].getMethodName(),"user.created",HttpStatus.CREATED.value()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> createPublisherEditor(PublisherEditor publisherEditor) {
        try{
            userRepository.save(publisherEditor);
            log.info(LogUtil.getMessage(Thread.currentThread().getStackTrace()[1].getMethodName(),"publisher.created",HttpStatus.CREATED.value()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Page<DisplayNewsResponse>> displayNews(Pageable pageable) {
        return newsService.displayNewsForNonSubscriber(pageable);
    }

    @Override
    public ResponseEntity<HttpStatus> assignToPublisherEditor(Long userId) {

        try{
            Optional<User> userOptional = userRepository.findById(userId);
            if(userOptional.isEmpty()) throw new UserNotFoundException(userId);
            User user = userOptional.get();
            user.setUserType("publisher_editor");
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<HttpStatus> assignToSubscriber(Long userId) {
        try{
            Optional<User> userOptional = userRepository.findById(userId);
            if(userOptional.isEmpty()) throw new UserNotFoundException(userId);
            User user = userOptional.get();
            user.setUserType("subscriber");
            userRepository.save(user);
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

    @Override
    public ResponseEntity<HttpStatus> delete(Long userId) {
        try{
            userRepository.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
