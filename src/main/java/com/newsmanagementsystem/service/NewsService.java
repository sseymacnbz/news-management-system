package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface NewsService {
    ResponseEntity<List<Long>> newsContents();
    ResponseEntity<HttpStatus> save(News news);
    ResponseEntity<Page<DisplayNewsResponse>> displayNewsForSubscriber(Pageable pageable, Long userId);
    ResponseEntity<Page<DisplayNewsResponse>> displayNewsForNonSubscriber(Pageable pageable, Long userId);
    boolean isNewsExist (Long newsId);
    News findById(Long newsId);
    ResponseEntity<HttpStatus> delete(Long newsId);
    ResponseEntity<HttpStatus> deleteNewsByContents(List<Content> contentList);




}
