package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.AssignRequest;
import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.UpdateNewsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface MainEditorService {
    ResponseEntity<HttpStatus> createNews(CreateNewsRequest createNewsRequest);
    ResponseEntity<HttpStatus> assignPublisherEditor(AssignRequest assignRequest);
    ResponseEntity<HttpStatus> assignSubscriber(AssignRequest assignRequest);
    ResponseEntity<HttpStatus> updateNews(UpdateNewsRequest updateNewsRequest);
    
}
