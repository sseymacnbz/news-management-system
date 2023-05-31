package com.newsmanagementsystem.service;


import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface PublisherEditorService {

    ResponseEntity<HttpStatus> createContent(CreateContentRequest createContentRequest);

}
