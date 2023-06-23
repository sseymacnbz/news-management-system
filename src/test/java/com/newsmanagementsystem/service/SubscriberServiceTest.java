package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.UnauthorizedAccessException;
import com.newsmanagementsystem.service.impl.NewsServiceImpl;
import com.newsmanagementsystem.service.impl.SubscriberServiceImpl;
import com.newsmanagementsystem.service.impl.UserServiceImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {

    @Mock
    NewsServiceImpl newsService;
    @Mock
    UserServiceImpl userService;

    @InjectMocks
    SubscriberServiceImpl subscriberService;


    @Test
    @DisplayName("displayNews test")
    void display_shouldDisplayNews(){

        List<DisplayNewsResponse> displayNewsResponseList = new ArrayList<>();
        Pageable pageableResponse = PageRequest.of(0, 2);
        ResponseEntity<Page<DisplayNewsResponse>> expected =
                new ResponseEntity<>(new PageImpl<>(displayNewsResponseList,pageableResponse,5),HttpStatus.OK);

        doReturn(true).when(userService).existsUserById(2L);
        doReturn(expected).when(newsService).displayNewsForSubscriber(pageableResponse,2L);
        doThrow(UnauthorizedAccessException.class).when(userService).existsUserById(100L);

        assertAll(
                () -> assertThrows(UnauthorizedAccessException.class,() -> subscriberService.displayNews(100L, pageableResponse)),
                () -> assertDoesNotThrow(() -> subscriberService.displayNews(2L,pageableResponse))
        );
    }
}
