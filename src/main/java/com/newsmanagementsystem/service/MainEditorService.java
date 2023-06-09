package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreatePublisherEditorRequest;
import com.newsmanagementsystem.dto.requests.MainEditorRequest;
import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.UpdateNewsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface MainEditorService {
    ResponseEntity<HttpStatus> createNews(CreateNewsRequest createNewsRequest);
    ResponseEntity<HttpStatus> createPublisherEditor(CreatePublisherEditorRequest createPublisherEditorRequest);
    ResponseEntity<HttpStatus> assignPublisherEditor(MainEditorRequest mainEditorRequest);
    ResponseEntity<HttpStatus> assignSubscriber(MainEditorRequest mainEditorRequest);
    ResponseEntity<HttpStatus> updateNews(UpdateNewsRequest updateNewsRequest);
    ResponseEntity<HttpStatus> deleteNews(MainEditorRequest mainEditorRequest);
    ResponseEntity<HttpStatus> deleteSubscriber(MainEditorRequest mainEditorRequest);
    ResponseEntity<HttpStatus> deletePublisherEditor(MainEditorRequest mainEditorRequest);
    ResponseEntity<HttpStatus> deleteContent(MainEditorRequest mainEditorRequest);


}
