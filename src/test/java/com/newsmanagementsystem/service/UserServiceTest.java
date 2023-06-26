package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.PublisherEditorNotFoundException;
import com.newsmanagementsystem.mapper.UserMapper;
import com.newsmanagementsystem.model.MainEditor;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.Subscriber;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.repository.UserRepository;
import com.newsmanagementsystem.service.impl.ContentServiceImpl;
import com.newsmanagementsystem.service.impl.NewsServiceImpl;
import com.newsmanagementsystem.service.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    NewsServiceImpl newsService;
    @Mock
    ContentServiceImpl contentService;

    @Test
    @DisplayName("createSubscriber test")
    void create_shouldCreateSubscriberSuccessfully(){

        Subscriber subscriber = new Subscriber();
        assertDoesNotThrow(() -> userService.createSubscriber(subscriber));

    }


    @Test
    @DisplayName("createPublisherEditor test")
    void create_shouldCreatePublisherEditorSuccessfully(){
        PublisherEditor publisherEditor = new PublisherEditor();
        assertDoesNotThrow(() -> userService.createPublisherEditor(publisherEditor));
    }

    @Test
    @DisplayName("displayNews test")
    void display_shouldDisplayNews(){
        /*
        List<DisplayNewsResponse> displayNewsResponseList = new ArrayList<>();
        Pageable pageableResponse = PageRequest.of(0, 2);
        ResponseEntity<Page<DisplayNewsResponse>> expected =
                new ResponseEntity<>(new PageImpl<>(displayNewsResponseList,pageableResponse,5),HttpStatus.OK);

        doReturn(expected).when(userService).displayNews(pageableResponse);

        ResponseEntity<Page<DisplayNewsResponse>> actual = userService.displayNews(pageableResponse);
        assertAll(
                () -> assertEquals(expected,actual),
                () -> assertEquals(3,actual.getBody().getTotalPages()),
                () -> assertEquals(5, actual.getBody().getTotalElements())
        );*/
        List<DisplayNewsResponse> displayNewsResponseList = new ArrayList<>();
        Pageable pageableResponse = PageRequest.of(0, 2);
        ResponseEntity<Page<DisplayNewsResponse>> expected =
                new ResponseEntity<>(new PageImpl<>(displayNewsResponseList,pageableResponse,5),HttpStatus.OK);

        doReturn(expected).when(newsService).displayNewsForNonSubscriber(pageableResponse);

        assertDoesNotThrow(() -> userService.displayNews(pageableResponse));

    }



    @Test
    @Transactional
    @DisplayName("assignToPublisherEditor test")
    void assign_shouldAssignToPublisherEditor(){
        User user = new User();
        user.setId(1L);
        doReturn(Optional.of(user)).when(userRepository).findById(1L);
        PublisherEditor publisherEditor = UserMapper.INSTANCE.convertToPublisherEditor(user);
        doReturn(publisherEditor).when(userRepository).save(publisherEditor);
        assertDoesNotThrow(() -> userService.assignToPublisherEditor(1L));


        //doReturn(publisherEditor).when(userRepository).save(publisherEditor);
        /*
        assertAll(
                () -> assertDoesNotThrow(() -> userService.assignToPublisherEditor(2L)),
                () -> assertEquals(expected, userService.assignToPublisherEditor(2L)),
                () -> assertEquals(PublisherEditor.class,userService.assignToPublisherEditor(2L).getBody().getClass())
        );*/
    }


    @Test
    @DisplayName("assignToSubscriber test")
    void assign_shouldAssignToSubscriber(){
        Subscriber subscriber = new Subscriber();
        ResponseEntity<Subscriber> expected = new ResponseEntity<>(subscriber,HttpStatus.OK);

        doReturn(expected).when(userService).assignToSubscriber(any());

        ResponseEntity<Subscriber> actual = userService.assignToSubscriber(any());

        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertEquals(Subscriber.class,actual.getBody().getClass())
        );

    }

    @Test
    @DisplayName("assignToSubscriberThrowException test")
    void throw_shouldThrowPublisherEditorHasContentsException(){
        Subscriber subscriber = new Subscriber();
        PublisherEditorNotFoundException expected = new PublisherEditorNotFoundException(7L);

        doThrow(expected).when(userService).assignToSubscriber(7L);

        assertAll(
                () -> assertThrows(PublisherEditorNotFoundException.class,
                        () -> userService.assignToSubscriber(7L))
        );

    }

    @Test
    @Transactional
    @DisplayName("findMainEditors test")
    void find_shouldFindMainEditors(){
        /*List<User> expected = new ArrayList<>();
        expected.add(new MainEditor());

        doReturn(expected).when(userService).findMainEditors();

        List<User> actual = userService.findMainEditors();

        assertAll(
                () -> assertEquals(expected,actual),
                () -> assertNotEquals(0,actual.size())
        );*/
        //List<User> expected = new ArrayList<>();
        assertDoesNotThrow(() -> userService.findMainEditors());
    }


    @Test
    @DisplayName("findPublisherEditors test")
    void find_shouldFindPublisherEditors(){
        List<User> expected = new ArrayList<>();
        expected.add(new PublisherEditor());

        doReturn(expected).when(userService).findPublisherEditors();

        List<User> actual = userService.findPublisherEditors();

        assertAll(
                () -> assertEquals(expected,actual),
                () -> assertEquals(PublisherEditor.class, actual.get(0).getClass())
        );
    }

    @Test
    @DisplayName("findSubscribers test")
    void find_shouldFindSubscribers(){
        List<User> expected = new ArrayList<>();
        expected.add(new Subscriber());

        doReturn(expected).when(userService).findSubscriberUsers();

        List<User> actual = userService.findSubscriberUsers();

        assertAll(
                () -> assertEquals(expected,actual),
                () -> assertEquals(Subscriber.class, actual.get(0).getClass())
        );
    }

    @Test
    @DisplayName("existsUserById test")
    void find_shouldFindExistenceOfUser(){
        boolean expected = true;
        doReturn(expected).when(userService).existsUserById(10L);

        boolean actual = userService.existsUserById(10L);

        assertAll(
                () -> assertEquals(expected,actual)
        );

    }

    @Test
    @DisplayName("delete test")
    void delete_shouldDeleteUser(){
        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.OK);
        doReturn(expected).when(userService).delete(10L);

        ResponseEntity<HttpStatus> actual = userService.delete(10L);

        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertNotEquals(true, userService.existsUserById(10L))
        );
    }

    @Test
    @DisplayName("getUser test")
    void get_shouldGetUser(){
        User user = new User();
        user.setId(10L);
        user.setUserType("publisher_editor");
        ResponseEntity<User> expected = new ResponseEntity<>(user,HttpStatus.OK);
        doReturn(expected).when(userService).getUser(10L);

        ResponseEntity<User> actual = userService.getUser(10L);

        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertEquals("publisher_editor",actual.getBody().getUserType())
        );

    }


}
