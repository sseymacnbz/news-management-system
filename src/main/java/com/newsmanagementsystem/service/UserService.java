package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreateUserRequest;
import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.model.MainEditor;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService{
     void createMainEditor(MainEditor mainEditor); //??
     ResponseEntity<HttpStatus> createPublicUser(CreateUserRequest createUserRequest);
     ResponseEntity<HttpStatus> createPublisherEditor(PublisherEditor publisherEditor);
     ResponseEntity<List<DisplayNewsResponse>> displayNews(Long userId);
     ResponseEntity<HttpStatus> assignToPublisherEditor(Long userId);
     ResponseEntity<HttpStatus> assignToSubscriber(Long userId);
     List<User> findMainEditors();
     List<User> findPublisherEditors();
     List<User> findSubscriberUsers();
     List<User> findNonSubscriberUsers();
     ResponseEntity<HttpStatus> delete(Long userId);

}
