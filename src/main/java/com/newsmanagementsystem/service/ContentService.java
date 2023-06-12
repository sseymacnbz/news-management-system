package com.newsmanagementsystem.service;

import com.newsmanagementsystem.model.Content;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContentService {
    ResponseEntity<HttpStatus> save(Content content);
    ResponseEntity<HttpStatus> delete(Long contentId);
    ResponseEntity<HttpStatus> deleteAllByPublisherEditorId(Long publisherEditorId);
    ResponseEntity<List<Content>> findAllByPublisherEditorId(Long publisherEditorId);
    ResponseEntity<Content> findById(Long id);
    boolean isContentExist(Long id);

}
