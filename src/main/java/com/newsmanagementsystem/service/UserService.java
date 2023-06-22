package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.Subscriber;
import com.newsmanagementsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService{

     ResponseEntity<HttpStatus> createSubscriber(Subscriber subscriber);
     ResponseEntity<HttpStatus> createPublisherEditor(PublisherEditor publisherEditor);
     ResponseEntity<Page<DisplayNewsResponse>> displayNews(Pageable pageable);
     ResponseEntity<PublisherEditor> assignToPublisherEditor(Long userId);
     ResponseEntity<Subscriber> assignToSubscriber(Long userId);
     List<User> findMainEditors();
     List<User> findPublisherEditors();
     List<User> findSubscriberUsers();
     boolean existsUserById(Long id);
     ResponseEntity<HttpStatus> delete(Long userId);
     ResponseEntity<User> getUser(Long userId);

}
