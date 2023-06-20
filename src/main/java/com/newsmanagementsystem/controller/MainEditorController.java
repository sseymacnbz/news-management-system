package com.newsmanagementsystem.controller;

import com.newsmanagementsystem.dto.requests.CreatePublisherEditorRequest;
import com.newsmanagementsystem.dto.requests.MainEditorRequest;
import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.UpdateNewsRequest;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.service.MainEditorService;
import com.newsmanagementsystem.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/getNewsContent")
    @PageableAsQueryParam
    public ResponseEntity<Page<Content>> getNewsContent(@RequestParam boolean contentWithNews, @Parameter(hidden = true) Pageable pageable){
        return new ResponseEntity<>(mainEditorService.getNewsContent(contentWithNews,pageable).getBody(), HttpStatus.OK);
    }
    @PostMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestBody MainEditorRequest mainEditorRequest){
        return new ResponseEntity<>(mainEditorService.getUser(mainEditorRequest).getBody(), HttpStatus.OK);
    }

    @PostMapping("/createNews")
    public ResponseEntity<HttpStatus> createNews(@RequestBody CreateNewsRequest createNewsRequest){
        return mainEditorService.createNews(createNewsRequest);
    }

    @PostMapping("/createPublisherEditor")
    public ResponseEntity<HttpStatus> createPublisherEditor(@RequestBody CreatePublisherEditorRequest createPublisherEditorRequest){
        return mainEditorService.createPublisherEditor(createPublisherEditorRequest);
    }

    @PutMapping("/assignPublisherEditor")
    public ResponseEntity<HttpStatus> assignPublisherEditor(@RequestBody MainEditorRequest mainEditorRequest){
        return mainEditorService.assignPublisherEditor(mainEditorRequest);
    }

    @PutMapping("/assignSubscriber")
    public ResponseEntity<HttpStatus> assignSubscriber(@RequestBody MainEditorRequest mainEditorRequest){
        return mainEditorService.assignSubscriber(mainEditorRequest);
    }

    @PutMapping("/updateNews")
    public ResponseEntity<HttpStatus> updateNews(@RequestBody UpdateNewsRequest updateNewsRequest){
        return mainEditorService.updateNews(updateNewsRequest);
    }

    @DeleteMapping("/deleteNews")
    public ResponseEntity<HttpStatus> deleteNews(@RequestBody MainEditorRequest mainEditorRequest){
        return mainEditorService.deleteNews(mainEditorRequest);
    }

    @DeleteMapping("/deleteSubscriber")
    public ResponseEntity<HttpStatus> deleteSubscriber(@RequestBody MainEditorRequest mainEditorRequest){
        return mainEditorService.deleteSubscriber(mainEditorRequest);
    }

    @DeleteMapping("/deletePublisherEditor")
    public ResponseEntity<HttpStatus> deletePublisherEditor(@RequestBody MainEditorRequest mainEditorRequest){
        return  mainEditorService.deletePublisherEditor(mainEditorRequest);
    }

    @DeleteMapping("/deleteContent")
    public ResponseEntity<HttpStatus> deleteContent(@RequestBody MainEditorRequest mainEditorRequest){
        return  mainEditorService.deleteContent(mainEditorRequest);
    }


}
