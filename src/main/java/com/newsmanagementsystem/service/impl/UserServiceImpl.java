package com.newsmanagementsystem.service.impl;


import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.PublisherEditorHasContentsException;
import com.newsmanagementsystem.mapper.UserMapper;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.Subscriber;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.repository.UserRepository;
import com.newsmanagementsystem.service.ContentService;
import com.newsmanagementsystem.service.NewsService;
import com.newsmanagementsystem.service.UserService;
import com.newsmanagementsystem.utilities.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    private ContentService contentService;
    public UserServiceImpl(@Lazy ContentService contentService){ this.contentService = contentService; }

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseEntity<HttpStatus> createSubscriber(Subscriber subscriber) {
        try{
            userRepository.save(subscriber);
            log.info(LogUtil.getMessage(Thread.currentThread().getStackTrace()[1].getMethodName(),"subscriber.created",HttpStatus.CREATED.value()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
    public ResponseEntity<PublisherEditor> assignToPublisherEditor(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        PublisherEditor publisherEditor = UserMapper.INSTANCE.convertToPublisherEditor(userOptional.orElse(null));
        userRepository.save(publisherEditor);
        log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"publisher.assign", userId,HttpStatus.OK.value()));
        if(userOptional.isPresent()) {
            userRepository.deleteById(userOptional.get().getId());
            return new ResponseEntity<>(publisherEditor, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Subscriber> assignToSubscriber(Long userId) {
        try{
            Optional<User> userOptional = userRepository.findById(userId);
            List<Content> contentList= contentService.findAllByPublisherEditorId(userId).getBody();
            if(contentList == null || !contentList.isEmpty()) throw new PublisherEditorHasContentsException();
            Subscriber subscriber = UserMapper.INSTANCE.convertToSubscriber(userOptional.orElse(null));
            userRepository.save(subscriber);
            log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"subscriber.assign", userId,HttpStatus.OK.value()));
            if(userOptional.isPresent()){
                userRepository.deleteById(userOptional.get().getId());
                return new ResponseEntity<>(subscriber,HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(Exception e){
            throw e;
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
    public boolean existsPublisherEditorById(Long id){
        return findPublisherEditors().stream().anyMatch(publisherEditor -> publisherEditor.getId().equals(id));
    }
    @Override
    public boolean existsSubscriberById(Long id){
        return findSubscriberUsers().stream().anyMatch(subscriber -> subscriber.getId().equals(id));
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

    @Override
    public ResponseEntity<User> getUser(Long userId) {
        try{
            log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"user.display", userId,HttpStatus.OK.value()));
            return new ResponseEntity<>(userRepository.findById(userId).orElse(null), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
