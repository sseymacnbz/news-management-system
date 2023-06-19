package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.UnauthorizedAccessException;
import com.newsmanagementsystem.service.NewsService;
import com.newsmanagementsystem.service.SubscriberService;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        if(userService.existsUserById(userId)){
            return newsService.displayNewsForSubscriber(pageable,userId);
        }
        else throw new UnauthorizedAccessException();
    }
}
