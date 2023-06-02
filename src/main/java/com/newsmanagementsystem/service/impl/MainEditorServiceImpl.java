package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.mapper.NewsMapper;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.service.MainEditorService;
import com.newsmanagementsystem.service.NewsService;
import com.newsmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MainEditorServiceImpl implements MainEditorService {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<HttpStatus> createNews(CreateNewsRequest createNewsRequest) {

        boolean result = userService.findMainEditors().stream().anyMatch(editor-> editor.getId().equals(createNewsRequest.getMainEditorId()));

        if(result){
            News news = NewsMapper.INSTANCE.createNewsRequestToContent(createNewsRequest);
            newsService.save(news);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> assignPublisherEditor(Long userId){

        boolean result = userService.findSubscriberUsers().stream().anyMatch(user -> user.getId().equals(userId));

        if(result){
            userService.assignToPublisherEditor(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
