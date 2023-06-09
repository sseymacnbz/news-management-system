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
class MainEditorServiceTest {

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

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);

        doReturn(true).when(userService).existsUserById(2L);

        assertDoesNotThrow(() -> mainEditorService.getUser(mainEditorRequest));

        MainEditorRequest mainEditorRequest1 = new MainEditorRequest(100L,2L);
        assertThrows(MainEditorNotFoundException.class,() -> mainEditorService.getUser(mainEditorRequest1));

        MainEditorRequest mainEditorRequest2 = new MainEditorRequest(1L,200L);
        assertThrows(UserNotFoundException.class, () -> mainEditorService.getUser(mainEditorRequest2));
    }

    @Test
    @DisplayName("createNews test")
    void create_shouldCreateNews(){
        CreateNewsRequest createNewsRequest = new CreateNewsRequest();
        createNewsRequest.setMainEditorId(1L);
        createNewsRequest.setContentId(2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);

        doReturn(true).when(contentService).isContentExist(2L);

        News news = NewsMapper.INSTANCE.createNewsRequestToNews(createNewsRequest);

        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(expected).when(newsService).save(news);

        assertDoesNotThrow(() -> mainEditorService.createNews(createNewsRequest));

        createNewsRequest.setMainEditorId(100L);
        assertThrows(MainEditorNotFoundException.class, () -> mainEditorService.createNews(createNewsRequest));

        createNewsRequest.setMainEditorId(1L);
        createNewsRequest.setContentId(200L);
        assertThrows(ContentNotFoundException.class, () -> mainEditorService.createNews(createNewsRequest));
    }

    @Test
    @DisplayName("createPublisherEditor test")
    void create_shouldCreatePublisherEditor(){
        CreatePublisherEditorRequest createPublisherEditorRequest = new CreatePublisherEditorRequest();
        createPublisherEditorRequest.setMainEditorId(1L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);

        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(expected).when(publisherEditorService).createPublisherEditor(createPublisherEditorRequest);

        assertDoesNotThrow(() -> mainEditorService.createPublisherEditor(createPublisherEditorRequest));

        createPublisherEditorRequest.setMainEditorId(100L);
        assertThrows(MainEditorNotFoundException.class,() -> mainEditorService.createPublisherEditor(createPublisherEditorRequest));
    }

    @Test
    @DisplayName("createSubscriber test")
    void create_shouldCreateSubscriber(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setMainEditorId(1L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);

        Subscriber subscriber = UserMapper.INSTANCE.convertToSubscriber(createUserRequest);
        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(expected).when(userService).createSubscriber(subscriber);

        assertDoesNotThrow(() -> mainEditorService.createSubscriber(createUserRequest));

        createUserRequest.setMainEditorId(100L);
        assertThrows(MainEditorNotFoundException.class, () -> mainEditorService.createSubscriber(createUserRequest));
    }

    @Test
    @DisplayName("assignPublisherEditor test")
    void assign_shouldAssignPublisherEditor(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);

        doReturn(true).when(userService).existsSubscriberById(2L);

        assertDoesNotThrow(() -> mainEditorService.assignPublisherEditor(mainEditorRequest));

        mainEditorRequest.setMainEditorId(100L);
        assertThrows(MainEditorNotFoundException.class, () -> mainEditorService.assignPublisherEditor(mainEditorRequest));

        mainEditorRequest.setMainEditorId(1L);
        mainEditorRequest.setId(200L);
        assertThrows(UserNotFoundException.class, () -> mainEditorService.assignPublisherEditor(mainEditorRequest));
    }

    @Test
    @DisplayName("assignSubscriber test")
    void assign_shouldAssignSubscriber(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);

        doReturn(true).when(userService).existsPublisherEditorById(2L);

        List<Content> contentList = new ArrayList<>();

        ResponseEntity<List<Content>> responseList = new ResponseEntity<>(contentList, HttpStatus.OK);
        doReturn(responseList).when(contentService).findAllByPublisherEditorId(2L);

        ResponseEntity<Subscriber> expected = new ResponseEntity<>(new Subscriber(),HttpStatus.OK);
        doReturn(expected).when(userService).assignToSubscriber(mainEditorRequest.getId());

        assertDoesNotThrow(() -> mainEditorService.assignSubscriber(mainEditorRequest));

        mainEditorRequest.setMainEditorId(100L);
        assertThrows(MainEditorNotFoundException.class, () -> mainEditorService.assignSubscriber(mainEditorRequest));

        contentList.add(new Content(1L));
        mainEditorRequest.setMainEditorId(1L);
        assertThrows(PublisherEditorHasContentsException.class, () -> mainEditorService.assignSubscriber(mainEditorRequest));
    }

    @Test
    @DisplayName("updateNews test")
    void update_shouldUpdateNews(){
        UpdateNewsRequest updateNewsRequest = new UpdateNewsRequest();
        updateNewsRequest.setMainEditorId(1L);
        updateNewsRequest.setNewsId(2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);

        doReturn(true).when(newsService).isNewsExist(2L);

        News news = NewsMapper.INSTANCE.updateNewsRequestToNews(updateNewsRequest);
        Content content = new Content();
        content.setId(3L);
        news.setContent(content);

        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);

        doReturn(expected).when(newsService).save(news);
        doReturn(news).when(newsService).findById(2L);
        assertDoesNotThrow(() -> mainEditorService).updateNews(updateNewsRequest);

        updateNewsRequest.setMainEditorId(100L);
        assertThrows(MainEditorNotFoundException.class, () -> mainEditorService.updateNews(updateNewsRequest));

        UpdateNewsRequest updateNewsRequest1 = new UpdateNewsRequest();
        updateNewsRequest1.setMainEditorId(1L);
        updateNewsRequest1.setNewsId(200L);

        assertThrows(NewsNotFoundException.class, () -> mainEditorService.updateNews(updateNewsRequest1));
    }

    @Test
    @DisplayName("deleteNews test")
    void delete_shouldDeleteNews(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);

        doReturn(true).when(newsService).isNewsExist(2L);

        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.OK);
        doReturn(expected).when(newsService).delete(2L);

        assertDoesNotThrow(() -> mainEditorService.deleteNews(mainEditorRequest));

        mainEditorRequest.setId(3L);
        assertThrows(NewsNotFoundException.class, () -> mainEditorService.deleteNews(mainEditorRequest));

        mainEditorRequest.setMainEditorId(3L);
        assertThrows(MainEditorNotFoundException.class, () -> mainEditorService.deleteNews(mainEditorRequest));
    }

    @Test
    @DisplayName("deleteSubscriber test")
    void delete_shouldDeleteSubscriber(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);

        doReturn(true).when(userService).existsSubscriberById(2L);

        assertDoesNotThrow(() -> mainEditorService.deleteSubscriber(mainEditorRequest));

        mainEditorRequest.setId(3L);
        assertThrows(UserNotFoundException.class, () -> mainEditorService.deleteSubscriber(mainEditorRequest));

        mainEditorRequest.setMainEditorId(3L);
        assertThrows(MainEditorNotFoundException.class, () -> mainEditorService.deleteSubscriber(mainEditorRequest));
    }

    @Test
    @DisplayName("deletePublisherEditor test")
    void delete_shouldDeletePublisherEditor(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doReturn(true).when(userService).existsPublisherEditorById(2L);

        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.OK);
        doReturn(expected).when(contentService).deleteAllByPublisherEditorId(2L);
        doReturn(expected).when(userService).delete(2L);

        assertDoesNotThrow(() -> mainEditorService.deletePublisherEditor(mainEditorRequest));

        mainEditorRequest.setId(3L);
        assertThrows(PublisherEditorNotFoundException.class, () -> mainEditorService.deletePublisherEditor(mainEditorRequest));

        mainEditorRequest.setMainEditorId(3L);
        assertThrows(MainEditorNotFoundException.class, () -> mainEditorService.deletePublisherEditor(mainEditorRequest));
    }

    @Test
    @DisplayName("deleteContent test")
    void delete_shouldDeleteContent(){
        MainEditorRequest mainEditorRequest = new MainEditorRequest(1L,2L);

        doReturn(true).when(mainEditorService).verifyMainEditor(1L);
        doReturn(true).when(contentService).isContentExist(2L);

        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.OK);
        doReturn(expected).when(contentService).delete(2L);

        assertDoesNotThrow(() -> mainEditorService.deleteContent(mainEditorRequest));

        mainEditorRequest.setId(3L);
        assertThrows(ContentNotFoundException.class, () -> mainEditorService.deleteContent(mainEditorRequest));

        mainEditorRequest.setMainEditorId(3L);
        assertThrows(MainEditorNotFoundException.class, () -> mainEditorService.deleteContent(mainEditorRequest));
    }

    @Test
    @DisplayName("verifyMainEditor test")
    void verifyMainEditor(){
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        userList.add(user);
        doReturn(userList).when(userService).findMainEditors();

        assertDoesNotThrow(() -> mainEditorService.verifyMainEditor(1L));
    }
}
