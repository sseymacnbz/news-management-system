package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.exceptionhandler.exceptiontypes.ContentNotCreatedException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.ContentNotFoundException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.ContentsNotFoundException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.UserNotFoundException;
import com.newsmanagementsystem.model.BaseEntity;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.repository.ContentRepository;
import com.newsmanagementsystem.service.ContentService;
import com.newsmanagementsystem.service.NewsService;
import com.newsmanagementsystem.service.UserService;
import com.newsmanagementsystem.utilities.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private NewsService newsService;

    private UserService userService;
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    private static final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Override
    public ResponseEntity<Page<Content>> getContentsThatIsNews(Pageable pageable) {
        List<Long> newsContentList = newsService.newsContents().getBody();
        if(newsContentList!=null){
            List<Content> contentList = new ArrayList<>();
            newsContentList.stream().forEach(id->{
                contentList.add(contentRepository.findById(id).get());
            });

            Pageable pageableResponse = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
            int start = (int) pageableResponse.getOffset();
            int end = Math.min((start + pageableResponse.getPageSize()), contentList.size());
            List<Content> pageContent = contentList.subList(start, end);
            pageContent.stream().forEach(content -> log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"content.display",content.getId(),HttpStatus.OK.value())));
            return new ResponseEntity<>(new PageImpl<>(pageContent, pageableResponse, contentList.size()), HttpStatus.OK);
        }else throw new ContentsNotFoundException();
    }

    @Override
    public ResponseEntity<Page<Content>> getContentsThatIsNotNews(Pageable pageable) {
        Pageable pageableResponse = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        List<Long> newsContentList = newsService.newsContents().getBody();
        List<Long> allNews = contentRepository.findAll().stream().map(BaseEntity::getId).toList();
        List<Long> contentsThatIsNotNews = allNews.stream().filter(item-> !newsContentList.contains(item)).toList();

        if(newsContentList != null){
            List<Content> contentList = new ArrayList<>();
            contentsThatIsNotNews.stream().forEach(id->{
                contentList.add(contentRepository.findById(id).get());
            });

            int start = (int) pageableResponse.getOffset();
            int end = Math.min((start + pageableResponse.getPageSize()), contentList.size());
            List<Content> pageContent = contentList.subList(start, end);
            pageContent.stream().forEach(content -> log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"content.display",content.getId(),HttpStatus.OK.value())));
            return new ResponseEntity<>(new PageImpl<>(pageContent, pageableResponse, contentList.size()), HttpStatus.OK);
        } else{
            List<Content> contentList = contentRepository.findAll();
            int start = (int) pageableResponse.getOffset();
            int end = Math.min((start + pageableResponse.getPageSize()), contentList.size());
            List<Content> pageContent = contentList.subList(start, end);
            pageContent.stream().forEach(content -> log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"content.display",content.getId(),HttpStatus.OK.value())));
            return new ResponseEntity<>(new PageImpl<>(pageContent, pageableResponse, contentList.size()), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> save(Content content) {

        try{
            contentRepository.save(content);
            log.info(LogUtil.getMessage(Thread.currentThread().getStackTrace()[1].getMethodName(),"content.created",HttpStatus.CREATED.value()));
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
                log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"content.deleted",contentId,HttpStatus.OK.value()));
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
    public boolean isContentExist(Long id) {
        return contentRepository.existsContentById(id);
    }

}
