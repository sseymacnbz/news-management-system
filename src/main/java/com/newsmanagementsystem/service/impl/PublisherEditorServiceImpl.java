package com.newsmanagementsystem.service.impl;


import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.dto.requests.CreatePublisherEditorRequest;
import com.newsmanagementsystem.dto.responses.DisplayContentsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.PublisherEditorNotFoundException;
import com.newsmanagementsystem.mapper.ContentMapper;
import com.newsmanagementsystem.mapper.DisplayContentMapper;
import com.newsmanagementsystem.mapper.PublisherEditorMapper;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.service.ContentService;
import com.newsmanagementsystem.service.PublisherEditorService;
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

import java.util.List;

@Service
public class PublisherEditorServiceImpl implements PublisherEditorService {

    @Autowired
    private UserService userService;
    @Autowired
    private ContentService contentService;
    private static final Logger log = LoggerFactory.getLogger(PublisherEditorServiceImpl.class);
    @Override
    public ResponseEntity<HttpStatus> createContent(CreateContentRequest createContentRequest) {

        boolean result = userService.existsPublisherEditorById(createContentRequest.getPublisherEditorId());
        if(result){
            Content content = ContentMapper.INSTANCE.createContentRequestToContent(createContentRequest);
            contentService.save(content);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }throw new PublisherEditorNotFoundException(createContentRequest.getPublisherEditorId());
    }

    @Override
    public ResponseEntity<HttpStatus> createPublisherEditor(CreatePublisherEditorRequest createPublisherEditorRequest) {

        try{
            PublisherEditor publisherEditor = PublisherEditorMapper.INSTANCE.createPublisherEditorRequestToPublisherEditor(createPublisherEditorRequest);
            userService.createPublisherEditor(publisherEditor);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Page<DisplayContentsResponse>> displayContents(Pageable pageable, Long publisherEditorId) {
        boolean result = userService.existsPublisherEditorById(publisherEditorId);
        if(result){
            Pageable pageableResponse = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), pageable.getSort());
            List<Content> publisherEditorsContent = contentService.findAllByPublisherEditorId(publisherEditorId).getBody();
            if(publisherEditorsContent !=null){
                int start = (int) pageableResponse.getOffset();
                int end = Math.min((start + pageableResponse.getPageSize()), publisherEditorsContent.size());
                List<Content> pageContent = publisherEditorsContent.subList(start, end);
                pageContent.stream().forEach(news -> log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"content.display",news.getId(),HttpStatus.OK.value())));
                log.info(LogUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"publisher.display.content",publisherEditorId,HttpStatus.OK.value()));
                return new ResponseEntity<>(new PageImpl<>(DisplayContentMapper.INSTANCE.contentToDisplayContentResponse(pageContent), pageableResponse, publisherEditorsContent.size()),HttpStatus.OK);
            }

        }
        throw new PublisherEditorNotFoundException(publisherEditorId);
    }
}
