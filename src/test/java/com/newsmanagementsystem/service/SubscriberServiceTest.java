package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.service.impl.SubscriberServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {

    @Mock
    SubscriberServiceImpl subscriberService;


    @Test
    @DisplayName("displayNews test")
    void display_shouldDisplayNews(){
        List<DisplayNewsResponse> displayNewsResponseList = new ArrayList<>();
        Pageable pageableResponse = PageRequest.of(0, 2);
        ResponseEntity<Page<DisplayNewsResponse>> expected =
                new ResponseEntity<>(new PageImpl<>(displayNewsResponseList,pageableResponse,5),HttpStatus.OK);

        doReturn(expected).when(subscriberService).displayNews(2L,pageableResponse);

        ResponseEntity<Page<DisplayNewsResponse>> actual = subscriberService.displayNews(2L,pageableResponse);

        assertAll(
                () -> assertEquals(expected,actual),
                () -> assertEquals(3,actual.getBody().getTotalPages()),
                () -> assertEquals(5, actual.getBody().getTotalElements())
        );

    }
}
