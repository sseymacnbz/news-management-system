package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.service.PublisherEditorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public class PublisherEditorServiceImpl implements PublisherEditorService {

    @Override
    public ResponseEntity<String> createContent(@RequestBody CreateContentRequest createContentRequest) {

        
        return null;
    }
}
