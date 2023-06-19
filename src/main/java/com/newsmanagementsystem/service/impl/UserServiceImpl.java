package com.newsmanagementsystem.service.impl;


import com.newsmanagementsystem.dto.requests.CreateUserRequest;
import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.PublisherEditorHasContentsException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.UnauthorizedAccessException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.UserIsAlreadySubscriberException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.UserNotFoundException;
import com.newsmanagementsystem.mapper.UserMapper;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.Subscriber;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.repository.UserRepository;
import com.newsmanagementsystem.service.ContentService;
import com.newsmanagementsystem.service.NewsService;
import com.newsmanagementsystem.service.PublisherEditorService;
import com.newsmanagementsystem.service.UserService;
import com.newsmanagementsystem.utilities.LogUtil;
import lombok.Getter;
import lombok.Setter;
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

    @Getter
    @Setter
    private ContentService contentService;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseEntity<HttpStatus> createSubscriber(CreateUserRequest createUserRequest) {
        Subscriber user = UserMapper.INSTANCE.convertToSubscriber(createUserRequest);
        userRepository.save(user);
        log.info(LogUtil.getMessage(Thread.currentThread().getStackTrace()[1].getMethodName(),"subscriber.created",HttpStatus.CREATED.value()));
        return new ResponseEntity<>(HttpStatus.CREATED);
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
            PublisherEditor publisherEditor = UserMapper.INSTANCE.convertToPublisherEditor(userOptional.get());
            userRepository.save(publisherEditor);
            log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"publisher.assign", userId,HttpStatus.OK.value()));
            delete(userId);
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
            else if(contentService.findAllByPublisherEditorId(userId).getBody() != null) throw new PublisherEditorHasContentsException();
            Subscriber subscriber = UserMapper.INSTANCE.convertToSubscriber(userOptional.get());
            userRepository.save(subscriber);
            log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"subscriber.assign", userId,HttpStatus.OK.value()));
            userRepository.deleteById(userOptional.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<User> findMainEditors() { return userRepository.findByUserType("main_editor"); }

    @Override
    public List<User> findPublisherEditors() {
        return userRepository.findByUserType("publisher_editor");
    }

    @Override
    public List<User> findSubscriberUsers() {
        return userRepository.findByUserType("subscriber");
    }

    @Override
    public boolean existsUserById(Long id) {
        return userRepository.existsUserById(id);
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
