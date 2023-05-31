package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.service.PublisherEditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/publisherEditors")
public class PublisherEditorController {
    @Autowired
    private PublisherEditorService publisherEditorService;

    @PostMapping
    public ResponseEntity createContent(@RequestBody CreateContentRequest createContentRequest){
        return publisherEditorService.createContent(createContentRequest);
    }
}
