package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.mapper.NewsMapper;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.repository.NewsRepository;
import com.newsmanagementsystem.repository.UserRepository;
import com.newsmanagementsystem.service.MainEditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MainEditorServiceImpl implements MainEditorService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<HttpStatus> createNews(CreateNewsRequest createNewsRequest) {

        boolean result = userRepository.findMainEditors().stream().anyMatch(editor-> editor.getId().equals(createNewsRequest.getMainEditor().getId()));

        if(result){
            News news = NewsMapper.INSTANCE.createNewsRequestToContent(createNewsRequest);
            newsRepository.save(news);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> assignPublisherEditor(Long userId){

        boolean result = userRepository.findUsers().stream().anyMatch(user -> user.getId()==userId);

        if(result){
            userRepository.assignToPublisherEditor(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
