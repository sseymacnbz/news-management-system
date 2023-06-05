package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.requests.AssignRequest;
import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.UpdateNewsRequest;
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
            News news = NewsMapper.INSTANCE.createNewsRequestToNews(createNewsRequest);
            newsService.save(news);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> assignPublisherEditor(AssignRequest assignRequest){

        if(userService.findMainEditors().stream().anyMatch(mainEditor -> mainEditor.getId().equals(assignRequest.getMainEditorId()))){

            boolean result = userService.findSubscriberUsers().stream().anyMatch(user -> user.getId().equals(assignRequest.getUserId())) ||
                    userService.findNonSubscriberUsers().stream().anyMatch(user -> user.getId().equals(assignRequest.getUserId()));

            if(result){
                userService.assignToPublisherEditor(assignRequest.getUserId());
                return new ResponseEntity<>(HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<HttpStatus> assignSubscriber(AssignRequest assignRequest) {

        if(userService.findMainEditors().stream().anyMatch(mainEditor -> mainEditor.getId().equals(assignRequest.getMainEditorId()))){
            boolean result = userService.findPublisherEditors().stream().anyMatch(user -> user.getId().equals(assignRequest.getUserId())) ||
                    userService.findNonSubscriberUsers().stream().anyMatch(user -> user.getId().equals(assignRequest.getUserId()));

            if (result) {
                userService.assignToSubscriber(assignRequest.getUserId());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> updateNews(UpdateNewsRequest updateNewsRequest) {

        if((newsService.findById(updateNewsRequest.getNewsId()) != null) &&
                userService.findMainEditors().stream().anyMatch(mainEditor->mainEditor.getId().equals(updateNewsRequest.getMainEditorId()))){
           News news = NewsMapper.INSTANCE.updateNewsRequestToNews(updateNewsRequest);
            newsService.findById(updateNewsRequest.getNewsId()).getContent().getId();
           newsService.save(news);
           return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
