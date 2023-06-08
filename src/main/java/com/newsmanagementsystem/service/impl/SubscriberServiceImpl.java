package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.service.NewsService;
import com.newsmanagementsystem.service.SubscriberService;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubscriberServiceImpl implements SubscriberService {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<Page<DisplayNewsResponse>> displayNews(Long userId, Pageable pageable) {

        boolean result = userService.findSubscriberUsers().stream().anyMatch(user->user.getId().equals(userId));
        if(result){
            return newsService.displayNewsForSubscriber(pageable);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
