package com.newsmanagementsystem.service;


import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import org.springframework.http.ResponseEntity;

public interface PublisherEditorService {

    ResponseEntity createContent(CreateContentRequest createContentRequest);

}
