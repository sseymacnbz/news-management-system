package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.UpdateNewsRequest;
import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.model.News;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NewsService {
    ResponseEntity<HttpStatus> save(News news);
    ResponseEntity<List<DisplayNewsResponse>> displayNewsForSubscriber();
    ResponseEntity<List<DisplayNewsResponse>> displayNewsForNonSubscriber();
    News findById (Long newsId);


}
