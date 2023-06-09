package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.service.SubscriberService;
import com.newsmanagementsystem.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/news")
public class NewsController {
    @Autowired
    private UserService userService;

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping("/getNews")
    @PageableAsQueryParam
    public ResponseEntity<Page<DisplayNewsResponse>> displayNews(@Parameter(hidden = true) Pageable pageable){
        return userService.displayNews(pageable);
    }
}
