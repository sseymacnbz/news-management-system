package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import org.springframework.http.ResponseEntity;

public interface MainEditorService {
    ResponseEntity createNews(CreateNewsRequest createNewsRequest);
}
