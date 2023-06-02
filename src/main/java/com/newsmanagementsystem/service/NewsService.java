package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.model.News;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NewsService {
    public ResponseEntity<HttpStatus> save(News news);

    public ResponseEntity<List<DisplayNewsResponse>> displayNewsForSubscriber();

    public ResponseEntity<List<DisplayNewsResponse>> displayNewsForNonSubscriber();

}
