package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubscriberService {
    ResponseEntity<List<DisplayNewsResponse>> displayNews(Long userId);
}
