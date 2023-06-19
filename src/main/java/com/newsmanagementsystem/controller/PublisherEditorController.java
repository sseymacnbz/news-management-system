package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.dto.responses.DisplayContentsResponse;
import com.newsmanagementsystem.service.PublisherEditorService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/publisherEditors")
public class PublisherEditorController {
    @Autowired
    private PublisherEditorService publisherEditorService;

    @GetMapping(value = "/getContents")
    @PageableAsQueryParam
    public ResponseEntity<Page<DisplayContentsResponse>> displayContents(@Parameter(hidden = true) Pageable pageable, @RequestParam Long publisherEditorId){
        return publisherEditorService.displayContents(pageable, publisherEditorId);
    }
    @PostMapping("/createContent")
    public ResponseEntity<HttpStatus> createContent(@RequestBody CreateContentRequest createContentRequest){
        return publisherEditorService.createContent(createContentRequest);
    }
}
