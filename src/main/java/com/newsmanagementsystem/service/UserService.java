package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreateUserRequest;
import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService{

     ResponseEntity<HttpStatus> createPublicUser(CreateUserRequest createUserRequest);
     ResponseEntity<HttpStatus> createPublisherEditor(PublisherEditor publisherEditor);
     ResponseEntity<Page<DisplayNewsResponse>> displayNews(Pageable pageable);
     ResponseEntity<HttpStatus> assignToPublisherEditor(Long userId);
     ResponseEntity<HttpStatus> assignToSubscriber(Long userId);
     List<Long> findMainEditors();
     List<Long> findPublisherEditors();
     List<Long> findSubscriberUsers();
     List<Long> findNonSubscriberUsers();
     ResponseEntity<HttpStatus> delete(Long userId);

}
