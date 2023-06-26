package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreateNewsRequest;
import com.newsmanagementsystem.dto.requests.MainEditorRequest;
import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.MainEditorNotFoundException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.UserNotFoundException;
import com.newsmanagementsystem.mapper.NewsMapper;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.service.impl.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MainEditorServiceTest {

    @Mock
    NewsServiceImpl newsService;
    @Mock
    UserServiceImpl userService;
    @Mock
    PublisherEditorServiceImpl publisherEditorService;
    @Mock
    ContentServiceImpl contentService;
    @Spy
    @InjectMocks
    MainEditorServiceImpl mainEditorService;


    @Test
    @DisplayName("getNewsContent test")
    void get_shouldGetNewsContent(){
        Pageable pageableResponse = PageRequest.of(0, 2);

        List<Content> contentList = new ArrayList<>();
        ResponseEntity<Page<Content>> expected =
                new ResponseEntity<>(new PageImpl<>(contentList,pageableResponse,5), HttpStatus.OK);
        doReturn(expected).when(contentService).getContentsThatIsNews(pageableResponse);
        assertDoesNotThrow(() -> mainEditorService.getNewsContent(true,pageableResponse));

        doReturn(expected).when(contentService).getContentsThatIsNotNews(pageableResponse);
        assertDoesNotThrow(() -> mainEditorService.getNewsContent(false,pageableResponse));
    }

    @Test
    @DisplayName("getUser test")
    void get_shouldGetUser(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);
        User user = new User();
        ResponseEntity<User> expected = new ResponseEntity<>(user,HttpStatus.OK);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(100L);

        doReturn(true).when(userService).existsUserById(2L);
        doThrow(UserNotFoundException.class).when(userService).existsUserById(100L);

        assertDoesNotThrow(() -> mainEditorService.getUser(mainEditorRequest));

    }
    /*
    @Test
    @DisplayName("createNews test")
    void create_shouldCreateNews(){
        CreateNewsRequest createNewsRequest = new CreateNewsRequest();
        createNewsRequest.setMainEditorId(1L);
        News news = NewsMapper.INSTANCE.createNewsRequestToNews(createNewsRequest);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(createNewsRequest.getMainEditorId());

        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(expected).when(newsService).save(news);

        assertDoesNotThrow(() -> mainEditorService.createNews(createNewsRequest));

    }*/


}
