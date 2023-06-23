package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.service.impl.NewsServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {
    @Mock
    NewsServiceImpl newsService;

    @Test
    @DisplayName("newsContents test")
    void get_shouldGetNewsContents(){
        ResponseEntity<List<Long>> expected = new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);

        doReturn(expected).when(newsService).newsContents();

        assertAll(
                () -> assertEquals(expected, newsService.newsContents())
        );
    }

    @Test
    @DisplayName("displayNewsForSubscriber test")
    void display_shouldDisplayNewsForSubscriber(){
        Pageable pageableResponse = PageRequest.of(0,2);
        List<DisplayNewsResponse> displayNewsResponseList = new ArrayList<>();
        ResponseEntity<Page<DisplayNewsResponse>> expected =
                new ResponseEntity<>(new PageImpl<>(displayNewsResponseList,pageableResponse,5),HttpStatus.OK);

        doReturn(expected).when(newsService).displayNewsForSubscriber(pageableResponse,10L);

        ResponseEntity<Page<DisplayNewsResponse>> actual = newsService.displayNewsForSubscriber(pageableResponse,10L);

        assertAll(
                () -> assertEquals(expected, newsService.displayNewsForSubscriber(pageableResponse,10L)),
                () -> assertEquals(3, actual.getBody().getTotalPages()),
                () -> assertEquals(5, actual.getBody().getTotalElements())
        );
    }

    @Test
    @DisplayName("displayNewsForNonSubscriber test")
    void display_shouldDisplayNewsForNonSubscriber(){
        Pageable pageableResponse = PageRequest.of(0, 2);
        List<DisplayNewsResponse> displayNewsResponseList = new ArrayList<>();
        ResponseEntity<Page<DisplayNewsResponse>> expected =
                new ResponseEntity<>(new PageImpl<>(displayNewsResponseList, pageableResponse, 5), HttpStatus.OK);

        doReturn(expected).when(newsService).displayNewsForNonSubscriber(pageableResponse);

        ResponseEntity<Page<DisplayNewsResponse>> actual = newsService.displayNewsForNonSubscriber(pageableResponse);

        assertAll(
                () -> assertEquals(expected, newsService.displayNewsForNonSubscriber(pageableResponse)),
                () -> assertEquals(3, actual.getBody().getTotalPages()),
                () -> assertEquals(5, actual.getBody().getTotalElements())
        );
    }

    @Test
    @DisplayName("isNewsExist test")
    void check_isNewsExist(){
        doReturn(true).when(newsService).isNewsExist(1L);
        doReturn(false).when(newsService).isNewsExist(100L);

        assertAll(
                () -> assertEquals(true,newsService.isNewsExist(1L)),
                () -> assertEquals(false, newsService.isNewsExist(100L)),
                () -> assertNotEquals(false, newsService.isNewsExist(1L)),
                () -> assertNotEquals(true,newsService.isNewsExist(100L))
        );

    }


}
