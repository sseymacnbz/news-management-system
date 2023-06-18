package com.newsmanagementsystem.service;


import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.dto.requests.CreatePublisherEditorRequest;
import com.newsmanagementsystem.dto.responses.DisplayContentsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface PublisherEditorService {
    ResponseEntity<HttpStatus> createContent(CreateContentRequest createContentRequest);
    ResponseEntity<HttpStatus> createPublisherEditor(CreatePublisherEditorRequest createPublisherEditorRequest);
    ResponseEntity<Page<DisplayContentsResponse>> displayContents(Pageable pageable, Long publisherEditorId);
}
