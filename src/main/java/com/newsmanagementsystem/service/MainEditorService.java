package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface MainEditorService {
    ResponseEntity<HttpStatus> createNews(CreateNewsRequest createNewsRequest);
    ResponseEntity<HttpStatus> assignPublisherEditor(Long userId);
}
