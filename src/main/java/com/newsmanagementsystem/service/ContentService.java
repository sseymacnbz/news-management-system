package com.newsmanagementsystem.service;

import com.newsmanagementsystem.model.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContentService {
    ResponseEntity<Page<Content>> getContentsThatIsNews(Pageable pageable);
    ResponseEntity<Page<Content>> getContentsThatIsNotNews(Pageable pageable);
    ResponseEntity<HttpStatus> save(Content content);
    ResponseEntity<HttpStatus> delete(Long contentId);
    ResponseEntity<HttpStatus> deleteAllByPublisherEditorId(Long publisherEditorId);
    ResponseEntity<List<Content>> findAllByPublisherEditorId(Long publisherEditorId);
    boolean isContentExist(Long id);

}
