package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.repository.ContentRepository;
import com.newsmanagementsystem.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public ResponseEntity<HttpStatus> save(Content content) {

        try{
            contentRepository.save(content);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
