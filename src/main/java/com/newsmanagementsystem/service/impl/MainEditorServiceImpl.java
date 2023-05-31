package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.mapper.NewsMapper;
import com.newsmanagementsystem.model.News;
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
    public ResponseEntity createNews(CreateNewsRequest createNewsRequest) {
        AtomicBoolean control = new AtomicBoolean(false);
        userRepository.findMainEditors().stream().forEach(editor->{
            if(editor.getId() == createNewsRequest.getMainEditor().getId()){control.set(true);}
        });

        if(control.get()){
            News news = NewsMapper.INSTANCE.createNewsRequestToContent(createNewsRequest);
            newsRepository.save(news);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
