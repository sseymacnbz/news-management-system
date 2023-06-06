package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.requests.CreatePublisherEditorRequest;
import com.newsmanagementsystem.dto.requests.MainEditorRequest;
import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.UpdateNewsRequest;
import com.newsmanagementsystem.mapper.NewsMapper;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.service.MainEditorService;
import com.newsmanagementsystem.service.NewsService;
import com.newsmanagementsystem.service.PublisherEditorService;
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
    @Autowired
    private PublisherEditorService publisherEditorService;


    @Override
    public ResponseEntity<HttpStatus> createNews(CreateNewsRequest createNewsRequest) {

        if(verifyMainEditor(createNewsRequest.getMainEditorId())){
            News news = NewsMapper.INSTANCE.createNewsRequestToNews(createNewsRequest);
            return newsService.save(news);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> createPublisherEditor(CreatePublisherEditorRequest createPublisherEditorRequest) {
        if(verifyMainEditor(createPublisherEditorRequest.getMainEditorId())){
            return publisherEditorService.createPublisherEditor(createPublisherEditorRequest);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> assignPublisherEditor(MainEditorRequest mainEditorRequest){

        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){

            boolean result = userService.findSubscriberUsers().stream().anyMatch(user -> user.getId().equals(mainEditorRequest.getId())) ||
                    userService.findNonSubscriberUsers().stream().anyMatch(user -> user.getId().equals(mainEditorRequest.getId()));

            if(result){
                return userService.assignToPublisherEditor(mainEditorRequest.getId());
            }

        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<HttpStatus> assignSubscriber(MainEditorRequest mainEditorRequest) {

        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){
            boolean result = userService.findPublisherEditors().stream().anyMatch(user -> user.getId().equals(mainEditorRequest.getId())) ||
                    userService.findNonSubscriberUsers().stream().anyMatch(user -> user.getId().equals(mainEditorRequest.getId()));

            if (result) {
                return userService.assignToSubscriber(mainEditorRequest.getId());
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> updateNews(UpdateNewsRequest updateNewsRequest) {

        if((newsService.findById(updateNewsRequest.getNewsId()) != null) &&
                verifyMainEditor(updateNewsRequest.getMainEditorId())){
           News news = NewsMapper.INSTANCE.updateNewsRequestToNews(updateNewsRequest);
            newsService.findById(updateNewsRequest.getNewsId()).getContent().getId();
            return newsService.save(news);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteNews(MainEditorRequest mainEditorRequest) {
        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){
            return newsService.delete(mainEditorRequest.getId());
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteSubscriber(MainEditorRequest mainEditorRequest) {
        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){
            if (userService.findSubscriberUsers().stream().anyMatch(subscriber -> subscriber.getId().equals(mainEditorRequest.getId()))){
                return userService.delete(mainEditorRequest.getId());
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> deletePublisherEditor(MainEditorRequest mainEditorRequest) {
        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){
            if (userService.findPublisherEditors().stream().anyMatch(publisherEditor -> publisherEditor.getId().equals(mainEditorRequest.getId()))){
                return userService.delete(mainEditorRequest.getId());
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean verifyMainEditor(Long mainEditorId){
        if(userService.findMainEditors().stream().anyMatch(mainEditor -> mainEditor.getId().equals(mainEditorId))){
            return true;
        }
        return false;
    }
}
