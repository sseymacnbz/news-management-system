package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.model.MainEditor;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mainEditors")
public class MainEditorController{

    @Autowired
    private UserService userService;

    @PostMapping
    public void create(@RequestBody MainEditor mainEditor){
        userService.create(mainEditor);
    }

    @PostMapping("/createNews")
    public void createNews(@RequestBody News news){

    }

}
