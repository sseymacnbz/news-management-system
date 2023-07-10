package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.*;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface MainEditorService {
    ResponseEntity<Page<Content>> getNewsContent(boolean contentWithNews, Pageable pageable);
    ResponseEntity<User> getUser(MainEditorRequest mainEditorRequest);
    ResponseEntity<HttpStatus> createNews(CreateNewsRequest createNewsRequest);
    ResponseEntity<HttpStatus> createPublisherEditor(CreatePublisherEditorRequest createPublisherEditorRequest);
    ResponseEntity<HttpStatus> createSubscriber(CreateUserRequest createUserRequest);
    ResponseEntity<HttpStatus> assignPublisherEditor(MainEditorRequest mainEditorRequest);
    ResponseEntity<HttpStatus> assignSubscriber(MainEditorRequest mainEditorRequest);
    ResponseEntity<HttpStatus> updateNews(UpdateNewsRequest updateNewsRequest);
    ResponseEntity<HttpStatus> deleteNews(MainEditorRequest mainEditorRequest);
    ResponseEntity<HttpStatus> deleteSubscriber(MainEditorRequest mainEditorRequest);
    ResponseEntity<HttpStatus> deletePublisherEditor(MainEditorRequest mainEditorRequest);
    ResponseEntity<HttpStatus> deleteContent(MainEditorRequest mainEditorRequest);



}
