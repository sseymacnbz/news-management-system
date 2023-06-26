package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.repository.NewsRepository;
import com.newsmanagementsystem.service.impl.NewsServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {
    @InjectMocks
    NewsServiceImpl newsService;

    @Mock
    NewsRepository newsRepository;

    @Test
    @DisplayName("newsContents test")
    void get_shouldGetNewsContents(){
        assertDoesNotThrow(() -> newsService.newsContents());
    }

    @Test
    @DisplayName("save test")
    @Transactional
    @Rollback
    void save_shouldSaveNews(){
        News news = new News();
        doReturn(news).when(newsRepository).save(news);
        assertDoesNotThrow(() -> newsService.save(news));
    }

    @Test
    @DisplayName("displayNewsForSubscriber test")
    void display_shouldDisplayNewsForSubscriber(){
        Pageable pageableResponse = PageRequest.of(0,2);
        assertDoesNotThrow(() -> newsService.displayNewsForSubscriber(pageableResponse,100L));
    }

    @Test
    @DisplayName("displayNewsForNonSubscriber test")
    void display_shouldDisplayNewsForNonSubscriber(){
        Pageable pageableResponse = PageRequest.of(0, 2);
        assertDoesNotThrow(() -> newsService.displayNewsForNonSubscriber(pageableResponse));
    }

    @Test
    @Transactional
    @DisplayName("isNewsExist test")
    void check_isNewsExist(){
        doReturn(true).when(newsRepository).existsNewsById(1L);
        assertDoesNotThrow(() -> newsService.isNewsExist(1L));
    }

    @Test
    @Transactional
    @DisplayName("findById test")
    void find_shouldFindNewsById(){
        News news = new News();
        news.setId(2L);

        doReturn(news).when(newsRepository).getReferenceById(3L);

        assertDoesNotThrow(() -> newsService.findById(3L));
    }



}
