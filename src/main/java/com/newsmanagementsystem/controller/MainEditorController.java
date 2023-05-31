package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.model.MainEditor;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.service.MainEditorService;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mainEditors")
public class MainEditorController{

    @Autowired
    private UserService userService;
    @Autowired
    private MainEditorService mainEditorService;

    @PostMapping
    public void create(@RequestBody MainEditor mainEditor){
        userService.create(mainEditor);
    }

    @PostMapping("/createNews")
    public ResponseEntity<HttpStatus> createNews(@RequestBody CreateNewsRequest createNewsRequest){
        return mainEditorService.createNews(createNewsRequest);
    }

    @PostMapping("/assignPublisherEditor")
    public ResponseEntity<HttpStatus> assignPublisherEditor(@RequestBody Long userId){
        return mainEditorService.assignPublisherEditor(userId);
    }

}
