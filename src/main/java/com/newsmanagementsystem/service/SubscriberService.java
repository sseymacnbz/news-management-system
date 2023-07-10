package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;


public interface SubscriberService {
    ResponseEntity<Page<DisplayNewsResponse>> displayNews(Long userId, Pageable pageable);
}
