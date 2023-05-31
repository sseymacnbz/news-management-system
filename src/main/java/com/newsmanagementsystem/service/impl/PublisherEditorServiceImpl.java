package com.newsmanagementsystem.service.impl;


import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.mapper.ContentMapper;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.repository.ContentRepository;
import com.newsmanagementsystem.repository.UserRepository;
import com.newsmanagementsystem.service.PublisherEditorService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class PublisherEditorServiceImpl implements PublisherEditorService {

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public ResponseEntity<HttpStatus> createContent(CreateContentRequest createContentRequest) {

        boolean result = userRepository.findPublisherEditors().stream().anyMatch(editor-> editor.getId().equals(createContentRequest.getPublisherEditor().getId()));

        if(result){
            Content content = ContentMapper.INSTANCE.createContentRequestToContent(createContentRequest);
            contentRepository.save(content);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
