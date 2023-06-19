package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.requests.CreatePublisherEditorRequest;
import com.newsmanagementsystem.dto.requests.MainEditorRequest;
import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.UpdateNewsRequest;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.ContentNotFoundException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.MainEditorNotFoundException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.NewsNotFoundException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.UserNotFoundException;
import com.newsmanagementsystem.mapper.NewsMapper;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.service.*;
import com.newsmanagementsystem.utilities.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
public class MainEditorServiceImpl implements MainEditorService {
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
    public ResponseEntity<Page<Content>> getNewsContent(boolean contentWithNews, Pageable pageable) {

        if(contentWithNews){
            return new ResponseEntity<>(contentService.getContentsThatIsNews(pageable).getBody(),HttpStatus.OK);
        }else return new ResponseEntity<>(contentService.getContentsThatIsNotNews(pageable).getBody(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> createNews(CreateNewsRequest createNewsRequest){
        if(verifyMainEditor(createNewsRequest.getMainEditorId())){
            News news = NewsMapper.INSTANCE.createNewsRequestToNews(createNewsRequest);
            newsService.save(news);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else throw new MainEditorNotFoundException(createNewsRequest.getMainEditorId());
    }

    @Override
    public ResponseEntity<HttpStatus> createPublisherEditor(CreatePublisherEditorRequest createPublisherEditorRequest) {
        if(verifyMainEditor(createPublisherEditorRequest.getMainEditorId())){
            publisherEditorService.createPublisherEditor(createPublisherEditorRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else throw new MainEditorNotFoundException(createPublisherEditorRequest.getMainEditorId());
    }

    @Override
    public ResponseEntity<HttpStatus> assignPublisherEditor(MainEditorRequest mainEditorRequest){

        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){

            boolean result = userService.findSubscriberUsers().stream().anyMatch(userId -> userId.equals(mainEditorRequest.getId())) ||
                    userService.findNonSubscriberUsers().stream().anyMatch(userId -> userId.equals(mainEditorRequest.getId()));

            if(result){
                userService.assignToPublisherEditor(mainEditorRequest.getId());
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else throw new UserNotFoundException(mainEditorRequest.getId());
        }
        else throw new MainEditorNotFoundException(mainEditorRequest.getMainEditorId());
    }

    @Override
    public ResponseEntity<HttpStatus> assignSubscriber(MainEditorRequest mainEditorRequest) {

        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){
            boolean result = userService.findPublisherEditors().stream().anyMatch(userId -> userId.equals(mainEditorRequest.getId())) ||
                    userService.findNonSubscriberUsers().stream().anyMatch(userId -> userId.equals(mainEditorRequest.getId()));

            if (result) {
                userService.assignToSubscriber(mainEditorRequest.getId());
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else throw new UserNotFoundException(mainEditorRequest.getId());
        }
        else throw new MainEditorNotFoundException(mainEditorRequest.getMainEditorId());
    }

    @Override
    public ResponseEntity<HttpStatus> updateNews(UpdateNewsRequest updateNewsRequest) {
        if(verifyMainEditor(updateNewsRequest.getMainEditorId())) {
            if ((newsService.findById(updateNewsRequest.getNewsId()) != null)) {

                News news = NewsMapper.INSTANCE.updateNewsRequestToNews(updateNewsRequest);
                news.setContent(new Content(newsService.findById(updateNewsRequest.getNewsId()).getContent().getId()));
                newsService.save(news);
                log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(), "news.updated", updateNewsRequest.getNewsId(),HttpStatus.OK.value()));
                return new ResponseEntity<>(HttpStatus.OK);
            }else throw new NewsNotFoundException(updateNewsRequest.getNewsId());
        } else throw new MainEditorNotFoundException(updateNewsRequest.getMainEditorId());
    }

    @Override
    public ResponseEntity<HttpStatus> deleteNews(MainEditorRequest mainEditorRequest) {
        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){
            if(newsService.isNewsExist(mainEditorRequest.getId())){
                newsService.delete(mainEditorRequest.getId());
                return new ResponseEntity<>(HttpStatus.OK);
            }else throw new NewsNotFoundException(mainEditorRequest.getId());
        }
        else throw new MainEditorNotFoundException(mainEditorRequest.getMainEditorId());
    }

    @Override
    public ResponseEntity<HttpStatus> deleteSubscriber(MainEditorRequest mainEditorRequest) {

        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){
            if(userService.findSubscriberUsers().stream().anyMatch(subscriberId -> subscriberId.equals(mainEditorRequest.getId()))){
                userService.delete(mainEditorRequest.getId());
                log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"subscriber.deleted", mainEditorRequest.getId(),HttpStatus.OK.value()));
                return new ResponseEntity<>(HttpStatus.OK);
            } else throw new UserNotFoundException(mainEditorRequest.getId());
        }else throw new MainEditorNotFoundException(mainEditorRequest.getMainEditorId());
    }

    @Override
    public ResponseEntity<HttpStatus> deletePublisherEditor(MainEditorRequest mainEditorRequest) {

        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){
            if(userService.findPublisherEditors().stream().anyMatch(publisherEditorId -> publisherEditorId.equals(mainEditorRequest.getId()))){
                contentService.deleteAllByPublisherEditorId(mainEditorRequest.getId());
                userService.delete(mainEditorRequest.getId());
                log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"publisher.deleted", mainEditorRequest.getId(),HttpStatus.OK.value()));
                return new ResponseEntity<>(HttpStatus.OK);
            }else throw new UserNotFoundException(mainEditorRequest.getId());
        }else throw new MainEditorNotFoundException(mainEditorRequest.getMainEditorId());
    }

    @Override
    public ResponseEntity<HttpStatus> deleteContent(MainEditorRequest mainEditorRequest) {

        if(verifyMainEditor(mainEditorRequest.getMainEditorId())){
            if(contentService.isContentExist(mainEditorRequest.getId())){
                contentService.delete(mainEditorRequest.getId());
                return new ResponseEntity<>(HttpStatus.OK);
            }throw new ContentNotFoundException(mainEditorRequest.getId());
        }else throw new MainEditorNotFoundException(mainEditorRequest.getMainEditorId());
    }


    private boolean verifyMainEditor(Long mainEditorId){
        return userService.findMainEditors().stream().anyMatch(id -> id.equals(mainEditorId));
    }
}
