package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.exceptionhandler.exceptiontypes.ContentNotCreatedException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.ContentNotFoundException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.UserNotFoundException;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.repository.ContentRepository;
import com.newsmanagementsystem.service.ContentService;
import com.newsmanagementsystem.service.NewsService;
import com.newsmanagementsystem.service.UserService;
import com.newsmanagementsystem.utilities.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private NewsService newsService;
    @Autowired
    private LogUtil logUtil;

    private UserService userService;
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    private static final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Override
    public ResponseEntity<HttpStatus> save(Content content) {

        try{
            contentRepository.save(content);
            log.info(logUtil.getMessage(Thread.currentThread().getStackTrace()[1].getMethodName(),"content.created",HttpStatus.CREATED.value()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            throw new ContentNotCreatedException();
        }
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long contentId) {
        if(contentRepository.existsContentById(contentId)){
            try{
                newsService.deleteNewsByContents(contentRepository.findById(contentId).stream().toList());
                contentRepository.deleteById(contentId);
                log.info(logUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"content.deleted",contentId,HttpStatus.OK.value()));
                return new ResponseEntity<>(HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else throw new ContentNotFoundException(contentId);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAllByPublisherEditorId(Long publisherEditorId) {

        if(userService.findPublisherEditors().stream().anyMatch(publisherEditor->publisherEditor.getId().equals(publisherEditorId))){
            try{
                contentRepository.findAllByPublisherEditorId(publisherEditorId).stream().forEach(content -> delete(content.getId()));
                return new ResponseEntity<>(HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else throw new UserNotFoundException(publisherEditorId);
    }

    @Override
    public ResponseEntity<List<Content>> findAllByPublisherEditorId(Long publisherEditorId) {
        if(userService.findPublisherEditors().stream().anyMatch(publisherEditor->publisherEditor.getId().equals(publisherEditorId))){
            try{
                return new ResponseEntity<>(contentRepository.findAllByPublisherEditorId(publisherEditorId), HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else throw new UserNotFoundException(publisherEditorId);
    }

    @Override
    public ResponseEntity<Content> findById(Long id) {
        return new ResponseEntity<>(contentRepository.getReferenceById(id), HttpStatus.OK);
    }

    @Override
    public boolean isContentExist(Long id) {
        return contentRepository.existsContentById(id);
    }

}
