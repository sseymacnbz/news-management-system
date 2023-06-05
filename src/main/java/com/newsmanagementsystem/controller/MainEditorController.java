package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.requests.AssignRequest;
import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.UpdateNewsRequest;
import com.newsmanagementsystem.model.MainEditor;
import com.newsmanagementsystem.service.MainEditorService;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/assignPublisherEditor")
    public ResponseEntity<HttpStatus> assignPublisherEditor(@RequestBody AssignRequest assignRequest){
        return mainEditorService.assignPublisherEditor(assignRequest);
    }

    @PutMapping("/assignSubscriber")
    public ResponseEntity<HttpStatus> assignSubscriber(@RequestBody AssignRequest assignRequest){
        return mainEditorService.assignSubscriber(assignRequest);
    }

    @PutMapping("/updateNews") // BURASI SORULACAK
    public ResponseEntity<HttpStatus> updateNews(@RequestBody UpdateNewsRequest updateNewsRequest){
        return mainEditorService.updateNews(updateNewsRequest);
    }

}
