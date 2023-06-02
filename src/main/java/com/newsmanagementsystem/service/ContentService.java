package com.newsmanagementsystem.service;

import com.newsmanagementsystem.model.Content;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ContentService {
    ResponseEntity<HttpStatus> save(Content content);
}
