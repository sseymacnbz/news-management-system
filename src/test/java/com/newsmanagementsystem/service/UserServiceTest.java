package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreateUserRequest;
import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.model.MainEditor;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.Subscriber;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserServiceImpl userService;

    @Test
    @DisplayName("createSubscriber test")
    void create_shouldCreateSubscriberSuccessfully(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);

        // Sanırım burda mock'lu servisteki bu metodun nasıl davranacağını belirttik. En azından ben öyle anladım.
        doReturn(expected).when(userService).createSubscriber(any());

        ResponseEntity<HttpStatus> actual = userService.createSubscriber(createUserRequest);

        assertAll(
                () -> assertEquals(expected, actual)
        );
    }


    @Test
    @DisplayName("createPublisherEditor test")
    void create_shouldCreatePublisherEditorSuccessfully(){
        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);

        doReturn(expected).when(userService).createPublisherEditor(any());

        ResponseEntity<HttpStatus> actual = userService.createPublisherEditor(any());

        assertAll(
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    @DisplayName("displayNews test")
    void get_shouldDisplayNews(){
        ResponseEntity<Page<DisplayNewsResponse>> expected = new ResponseEntity<>(HttpStatus.OK);
        Pageable pageableResponse = PageRequest.of(0,2);

        doReturn(expected).when(userService).displayNews(pageableResponse);

        ResponseEntity<Page<DisplayNewsResponse>> actual = userService.displayNews(pageableResponse);
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual)
        );
    }

    @Test
    @DisplayName("assignToPublisherEditor test")
    void assign_shouldAssignToPublisherEditor(){
        PublisherEditor publisherEditor = new PublisherEditor();
        ResponseEntity<PublisherEditor> expected = new ResponseEntity<>(publisherEditor,HttpStatus.OK);

        doReturn(expected).when(userService).assignToPublisherEditor(any());

        ResponseEntity<PublisherEditor> actual = userService.assignToPublisherEditor(any());

        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertEquals(PublisherEditor.class,actual.getBody().getClass())
        );

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
    @DisplayName("findMainEditors test")
    void find_shouldFindMainEditors(){
        List<User> expected = new ArrayList<>();
        expected.add(new MainEditor());

        doReturn(expected).when(userService).findMainEditors();

        List<User> actual = userService.findMainEditors();

        assertAll(
                () -> assertEquals(expected,actual),
                () -> assertNotEquals(0,actual.size())
        );
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
                () -> assertEquals("publisher_editor", actual.get(0).getUserType())
        );

    }
}
