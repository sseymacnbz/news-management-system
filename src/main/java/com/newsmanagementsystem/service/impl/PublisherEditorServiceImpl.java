package com.newsmanagementsystem.service.impl;


import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.dto.requests.CreatePublisherEditorRequest;
import com.newsmanagementsystem.mapper.ContentMapper;
import com.newsmanagementsystem.mapper.PublisherEditorMapper;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.service.ContentService;
import com.newsmanagementsystem.service.PublisherEditorService;
import com.newsmanagementsystem.service.UserService;
import com.newsmanagementsystem.utilities.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PublisherEditorServiceImpl implements PublisherEditorService {

    @Autowired
    private UserService userService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private LogUtil logUtil;

    @Override
    public ResponseEntity<HttpStatus> createContent(CreateContentRequest createContentRequest) {

        boolean result = userService.findPublisherEditors().stream().anyMatch(editor-> editor.getId().equals(createContentRequest.getPublisherEditorId()));

        if(result){
            Content content = ContentMapper.INSTANCE.createContentRequestToContent(createContentRequest);
            contentService.save(content);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> createPublisherEditor(CreatePublisherEditorRequest createPublisherEditorRequest) {

        try{
            PublisherEditor publisherEditor = PublisherEditorMapper.INSTANCE.createPublisherEditorRequestToPublisherEditor(createPublisherEditorRequest);
            userService.createPublisherEditor(publisherEditor);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
