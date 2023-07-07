package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.mapper.DisplayNewsMapper;
import com.newsmanagementsystem.mapper.UserMapper;
import com.newsmanagementsystem.model.*;
import com.newsmanagementsystem.repository.UserRepository;
import com.newsmanagementsystem.service.impl.ContentServiceImpl;
import com.newsmanagementsystem.service.impl.NewsServiceImpl;
import com.newsmanagementsystem.service.impl.UserServiceImpl;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    NewsServiceImpl newsService;
    @Mock
    ContentServiceImpl contentService;
    @Spy
    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("createSubscriber test")
    @Transactional
    @Rollback
    void create_shouldCreateSubscriberSuccessfully(){
        Subscriber subscriber = new Subscriber();
        doReturn(subscriber).when(userRepository).save(subscriber);

        assertDoesNotThrow(() -> userService.createSubscriber(subscriber));
    }


    @Test
    @DisplayName("createPublisherEditor test")
    @Transactional
    @Rollback
    void create_shouldCreatePublisherEditorSuccessfully(){
        PublisherEditor publisherEditor = new PublisherEditor();
        doReturn(publisherEditor).when(userRepository).save(publisherEditor);
        assertDoesNotThrow(() -> userService.createPublisherEditor(publisherEditor));
    }

        @Test
        @DisplayName("displayNews test")
        void display_shouldDisplayNews(){
            List<News> newsList = new ArrayList<>();
            News news = new News();
            news.setId(1L);
            newsList.add(news);
            Pageable pageableResponse = PageRequest.of(0, 2);
            ResponseEntity<Page<DisplayNewsResponse>> expected =
                    new ResponseEntity<>(new PageImpl<>(DisplayNewsMapper.INSTANCE.newsToDisplayNewsResponse(newsList),pageableResponse, newsList.size()), HttpStatus.OK);

            doReturn(expected).when(newsService).displayNewsForNonSubscriber(pageableResponse);

            assertDoesNotThrow(() -> userService.displayNews(pageableResponse));
        }

    @Test
    @DisplayName("assignToPublisherEditor test")
    @Transactional
    @Rollback
    void assign_shouldAssignToPublisherEditor(){
        User user = new User();
        user.setId(2L);
        Optional<User> optionalUser = Optional.of(user);
        doReturn(optionalUser).when(userRepository).findById(2L);

        PublisherEditor publisherEditor = UserMapper.INSTANCE.convertToPublisherEditor(optionalUser.orElse(null));

        doReturn(publisherEditor).when(userRepository).save(publisherEditor);

        assertDoesNotThrow(() -> userService.assignToPublisherEditor(2L));

    }


    @Test
    @DisplayName("assignToSubscriber test")
    void assign_shouldAssignToSubscriber(){
        User user = new User();
        user.setId(2L);
        Optional<User> optionalUser = Optional.of(user);
        doReturn(optionalUser).when(userRepository).findById(2L);

        Subscriber subscriber = UserMapper.INSTANCE.convertToSubscriber(optionalUser.orElse(null));

        assertDoesNotThrow(() -> userService.assignToSubscriber(2L));

    }

    @Test
    @Transactional
    @DisplayName("findMainEditors test")
    void find_shouldFindMainEditors(){
        List<User> expected = new ArrayList<>();
        expected.add(new MainEditor());

        doReturn(expected).when(userRepository).findByUserType("main_editor");
        assertDoesNotThrow(() -> userService.findMainEditors());
    }


    @Test
    @DisplayName("findPublisherEditors test")
    void find_shouldFindPublisherEditors(){
        List<User> expected = new ArrayList<>();
        expected.add(new PublisherEditor());

        doReturn(expected).when(userRepository).findByUserType("publisher_editor");
        assertDoesNotThrow(() -> userService.findPublisherEditors());

    }

    @Test
    @DisplayName("findSubscribers test")
    void find_shouldFindSubscribers(){
        List<User> expected = new ArrayList<>();
        expected.add(new Subscriber());

        doReturn(expected).when(userRepository).findByUserType("subscriber");
        assertDoesNotThrow(() -> userService.findSubscriberUsers());
    }

    @Test
    @DisplayName("existsUserById test")
    @Transactional
    void find_shouldFindExistenceOfUser(){
        doReturn(true).when(userRepository).existsUserById(2L);
        assertDoesNotThrow(() -> userService.existsUserById(2L));
    }

    @Test
    @DisplayName("delete test")
    @Transactional
    @Rollback
    void delete_shouldDeleteUser(){
        doNothing().when(userRepository).deleteById(10L);
        assertDoesNotThrow(() -> userService.delete(10L));
    }

    @Test
    @DisplayName("getUser test")
    void get_shouldGetUser(){
        User user = new User();
        user.setId(10L);

        doReturn(Optional.of(user)).when(userRepository).findById(10L);

        assertDoesNotThrow(() -> userService.getUser(10L));
    }

}
