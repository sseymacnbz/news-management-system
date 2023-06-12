package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.requests.CreatePublisherEditorRequest;
import com.newsmanagementsystem.dto.requests.MainEditorRequest;
import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.UpdateNewsRequest;
import com.newsmanagementsystem.mapper.NewsMapper;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.service.*;
import com.newsmanagementsystem.utilities.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MainEditorServiceImpl implements MainEditorService {
    @Autowired
    private LogUtil logUtil;
    private static final Logger log = LoggerFactory.getLogger(MainEditorServiceImpl.class);
    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;
    @Autowired
    private PublisherEditorService publisherEditorService;
    @Autowired
    private ContentService contentService;

    @Override
    public ResponseEntity<HttpStatus> createNews(CreateNewsRequest createNewsRequest) {

        if(verifyMainEditor(createNewsRequest.getMainEditorId())){
            News news = NewsMapper.INSTANCE.createNewsRequestToNews(createNewsRequest);
            newsService.save(news);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> createPublisherEditor(CreatePublisherEditorRequest createPublisherEditorRequest) {
        if(verifyMainEditor(createPublisherEditorRequest.getMainEditorId())){
            publisherEditorService.createPublisherEditor(createPublisherEditorRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> assignPublisherEditor(MainEditorRequest mainEditorRequest){

        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){

            boolean result = userService.findSubscriberUsers().stream().anyMatch(user -> user.getId().equals(mainEditorRequest.getId())) ||
                    userService.findNonSubscriberUsers().stream().anyMatch(user -> user.getId().equals(mainEditorRequest.getId()));

            if(result){
                userService.assignToPublisherEditor(mainEditorRequest.getId());
                log.info(logUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"publisher.assign", mainEditorRequest.getId()));
                return new ResponseEntity<>(HttpStatus.OK);
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
                userService.assignToSubscriber(mainEditorRequest.getId());
                log.info(logUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"subscriber.assign", mainEditorRequest.getId()));
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> updateNews(UpdateNewsRequest updateNewsRequest) {

        if((newsService.findById(updateNewsRequest.getNewsId()) != null) &&
                verifyMainEditor(updateNewsRequest.getMainEditorId())){
            News news = NewsMapper.INSTANCE.updateNewsRequestToNews(updateNewsRequest);
            news.setContent(new Content(newsService.findById(updateNewsRequest.getNewsId()).getContent().getId()));
            newsService.save(news);
            log.info(logUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"news.updated", updateNewsRequest.getNewsId()));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteNews(MainEditorRequest mainEditorRequest) {

        if(verifyMainEditor(mainEditorRequest.getMainEditorId()) &&
            newsService.isNewsExist(mainEditorRequest.getId())){
            newsService.delete(mainEditorRequest.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteSubscriber(MainEditorRequest mainEditorRequest) {
        if(verifyMainEditor(mainEditorRequest.getMainEditorId()) && userService.findSubscriberUsers().stream().anyMatch(subscriber -> subscriber.getId().equals(mainEditorRequest.getId()))){
            userService.delete(mainEditorRequest.getId());
            log.info(logUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"subscriber.deleted", mainEditorRequest.getId()));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> deletePublisherEditor(MainEditorRequest mainEditorRequest) {
        if(verifyMainEditor(mainEditorRequest.getMainEditorId()) &&
                userService.findPublisherEditors().stream().anyMatch(publisherEditor -> publisherEditor.getId().equals(mainEditorRequest.getId()))){
            userService.delete(mainEditorRequest.getId());
            log.info(logUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"publisher.deleted", mainEditorRequest.getId()));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteContent(MainEditorRequest mainEditorRequest) {
        if(verifyMainEditor(mainEditorRequest.getMainEditorId()) &&
                contentService.isContentExist(mainEditorRequest.getId())){
            contentService.delete(mainEditorRequest.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean verifyMainEditor(Long mainEditorId){
        return userService.findMainEditors().stream().anyMatch(mainEditor -> mainEditor.getId().equals(mainEditorId));
    }
}
