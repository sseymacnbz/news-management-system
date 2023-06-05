package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/subscriber")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping(value = "/news/{userId}")
    public ResponseEntity<List<DisplayNewsResponse>> displayNews(@PathVariable Long userId){
        return subscriberService.displayNews(userId);
    }

}
