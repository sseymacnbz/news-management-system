package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.NewsNotFoundException;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.model.enums.NewsTypeEnum;
import com.newsmanagementsystem.repository.NewsRepository;
import com.newsmanagementsystem.service.impl.NewsServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
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
@MockitoSettings(strictness = Strictness.LENIENT)
class NewsServiceTest {
    @Spy
    @InjectMocks
    NewsServiceImpl newsService;

    @Mock
    NewsRepository newsRepository;

    @Test
    @DisplayName("newsContents test")
    void get_shouldGetNewsContents(){
        List<Long> longList = new ArrayList<>();
        doReturn(longList).when(newsRepository).newsContents();
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
        List<News> newsList = new ArrayList<>();

        doReturn(newsList).when(newsRepository).findAllByIsHeadlineOrderByDateDesc(true);
        doReturn(newsList).when(newsRepository).findAllByIsHeadlineOrderByDateDesc(false);

        assertDoesNotThrow(() -> newsService.displayNewsForSubscriber(pageableResponse,100L));
    }

    @Test
    @DisplayName("displayNewsForNonSubscriber test")
    void display_shouldDisplayNewsForNonSubscriber(){
        Pageable pageableResponse = PageRequest.of(0, 2);
        List<News> newsList = new ArrayList<>();

        doReturn(newsList).when(newsRepository).findAllByIsHeadlineAndNewsTypeEnumOrderByDateDesc(true, NewsTypeEnum.FREE_NEWS);
        doReturn(newsList).when(newsRepository).findAllByIsHeadlineAndNewsTypeEnumOrderByDateDesc(false, NewsTypeEnum.FREE_NEWS);
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
        news.setId(3L);

        doReturn(news).when(newsRepository).getReferenceById(3L);

        assertDoesNotThrow(() -> newsService.findById(3L));
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("delete test")
    void delete_shouldDeleteNews(){

        doReturn(true).when(newsService).isNewsExist(2L);

        News news = new News();
        news.setId(2L);
        doReturn(news).when(newsRepository).getReferenceById(2L);
        doNothing().when(newsRepository).delete(news);

        assertDoesNotThrow(() -> newsService.delete(2L));
        assertThrows(NewsNotFoundException.class, () -> newsService.delete(3L));
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("deleteNewsByContents test")
    void delete_shouldDeleteNewsByContents(){
        List<Content> contentList = new ArrayList<>();
        contentList.add(new Content(1L));

        List<News> newsList = new ArrayList<>();
        News news = new News();
        news.setId(1L);
        news.setContent(new Content(1L));
        newsList.add(news);
        doReturn(newsList).when(newsRepository).findByContentId(1L);

        assertDoesNotThrow(() -> newsService.deleteNewsByContents(contentList));
    }

}
