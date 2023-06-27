package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.*;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.*;
import com.newsmanagementsystem.mapper.NewsMapper;
import com.newsmanagementsystem.mapper.UserMapper;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.model.Subscriber;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.service.impl.*;
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

    @Test
    @DisplayName("createNews test")
    void create_shouldCreateNews(){
        CreateNewsRequest createNewsRequest = new CreateNewsRequest();
        createNewsRequest.setMainEditorId(1L);
        News news = NewsMapper.INSTANCE.createNewsRequestToNews(createNewsRequest);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(2L);

        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(expected).when(newsService).save(news);

        assertDoesNotThrow(() -> mainEditorService.createNews(createNewsRequest));

    }

    @Test
    @DisplayName("createPublisherEditor test")
    void create_shouldCreatePublisherEditor(){
        CreatePublisherEditorRequest createPublisherEditorRequest = new CreatePublisherEditorRequest();
        createPublisherEditorRequest.setMainEditorId(1L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(100L);

        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(expected).when(publisherEditorService).createPublisherEditor(createPublisherEditorRequest);

        assertDoesNotThrow(() -> mainEditorService.createPublisherEditor(createPublisherEditorRequest));
    }

    @Test
    @DisplayName("createSubscriber test")
    void create_shouldCreateSubscriber(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setMainEditorId(1L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(100L);

        Subscriber subscriber = UserMapper.INSTANCE.convertToSubscriber(createUserRequest);
        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(expected).when(userService).createSubscriber(subscriber);

        assertDoesNotThrow(() -> mainEditorService.createSubscriber(createUserRequest));
    }

    @Test
    @DisplayName("assignPublisherEditor test")
    void assign_shouldAssignPublisherEditor(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(100L);

        doReturn(true).when(userService).existsSubscriberById(2L);
        doThrow(UserNotFoundException.class).when(userService).existsSubscriberById(3L);

        assertDoesNotThrow(() -> mainEditorService.assignPublisherEditor(mainEditorRequest));
    }

    @Test
    @DisplayName("assignSubscriber test")
    void assign_shouldAssignSubscriber(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(100L);

        doReturn(true).when(userService).existsPublisherEditorById(2L);
        doThrow(UserIsAlreadySubscriberException.class).when(userService).existsSubscriberById(3L);

        ResponseEntity<Subscriber> assigned = new ResponseEntity<>(new Subscriber(),HttpStatus.OK);
        doReturn(assigned).when(userService).assignToSubscriber(mainEditorRequest.getId());

        assertDoesNotThrow(() -> mainEditorService.assignSubscriber(mainEditorRequest));

    }

    @Test
    @DisplayName("updateNews test")
    void update_shouldUpdateNews(){
        UpdateNewsRequest updateNewsRequest = new UpdateNewsRequest();
        updateNewsRequest.setMainEditorId(1L);
        updateNewsRequest.setNewsId(2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(100L);

        News news = NewsMapper.INSTANCE.updateNewsRequestToNews(updateNewsRequest);
        Content content = new Content();
        content.setId(3L);
        news.setContent(content);

        doReturn(news).when(newsService).findById(2L);
        doReturn(null).when(newsService).findById(200L);

        assertDoesNotThrow(() -> mainEditorService).updateNews(updateNewsRequest);
    }

    @Test
    @DisplayName("deleteNews test")
    void delete_shouldDeleteNews(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(100L);

        doReturn(true).when(newsService).isNewsExist(2L);
        doThrow(NewsNotFoundException.class).when(newsService).isNewsExist(200L);

        assertDoesNotThrow(() -> mainEditorService.deleteNews(mainEditorRequest));
    }

    @Test
    @DisplayName("deleteSubscriber test")
    void delete_shouldDeleteSubscriber(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(100L);

        doReturn(true).when(userService).existsSubscriberById(2L);
        doThrow(UserNotFoundException.class).when(userService).existsSubscriberById(200L);

        assertDoesNotThrow(() -> mainEditorService.deleteSubscriber(mainEditorRequest));
    }

    @Test
    @DisplayName("deletePublisherEditor test")
    void delete_shouldDeletePublisherEditor(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(100L);

        doReturn(true).when(userService).existsPublisherEditorById(2L);
        doThrow(PublisherEditorNotFoundException.class).when(userService).existsPublisherEditorById(200L);

        assertDoesNotThrow(() -> mainEditorService.deletePublisherEditor(mainEditorRequest));
    }

    @Test
    @DisplayName("deleteContent test")
    void delete_shouldDeleteContent(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doThrow(MainEditorNotFoundException.class).when(mainEditorService).verifyMainEditor(100L);

        doReturn(true).when(contentService).isContentExist(2L);
        doThrow(ContentNotFoundException.class).when(contentService).isContentExist(200L);

        assertDoesNotThrow(() -> mainEditorService.deleteContent(mainEditorRequest));
    }
}
