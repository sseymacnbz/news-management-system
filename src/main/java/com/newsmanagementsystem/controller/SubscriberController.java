package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.service.SubscriberService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/subscriber")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping(value = "/news")
    @PageableAsQueryParam
    public ResponseEntity<Page<DisplayNewsResponse>> displayNews(@RequestParam Long userId, @Parameter(hidden = true) Pageable pageable){
        return subscriberService.displayNews(userId, pageable);
    }

}
