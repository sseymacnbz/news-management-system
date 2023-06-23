package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.dto.responses.DisplayContentsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.PublisherEditorNotFoundException;
import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;
import com.newsmanagementsystem.service.impl.PublisherEditorServiceImpl;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class PublisherEditorServiceTest {

    @Mock
    PublisherEditorServiceImpl publisherEditorService;

    @Test
    @DisplayName("createContent test")
    void create_shouldCreateContent(){
        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        CreateContentRequest createContentRequest = new CreateContentRequest(LocalDate.now(), ScopeEnum.AEGEAN_REGION, CategoryEnum.ECONOMY,"title","text",10L);
        doReturn(expected).when(publisherEditorService).createContent(createContentRequest);

        ResponseEntity<HttpStatus> actual = publisherEditorService.createContent(createContentRequest);

        assertAll(
                () ->  assertEquals(expected,actual)
        );
    }

    @Test
    @DisplayName("createContentThrowException test")
    void throw_shouldThrowPublisherEditorNotFoundException(){

        CreateContentRequest createContentRequest = new CreateContentRequest();
        createContentRequest.setPublisherEditorId(7L);

        PublisherEditorNotFoundException expected = new PublisherEditorNotFoundException(7L);

        doThrow(expected).when(publisherEditorService).createContent(createContentRequest);

        assertAll(
                () -> assertThrows(PublisherEditorNotFoundException.class,
                        () -> publisherEditorService.createContent(createContentRequest))
        );
    }


    @Test
    @DisplayName("displayContent test")
    void display_shouldDisplayContents(){

        List<DisplayContentsResponse> displayContentsResponseList = new ArrayList<>();
        Pageable pageableResponse = PageRequest.of(0, 2);

        ResponseEntity<Page<DisplayContentsResponse>> expected =
                new ResponseEntity<>(new PageImpl<>(displayContentsResponseList,pageableResponse,5),HttpStatus.OK);

        doReturn(expected).when(publisherEditorService).displayContents(pageableResponse,3L);

        ResponseEntity<Page<DisplayContentsResponse>> actual = publisherEditorService.displayContents(pageableResponse, 3L);

        assertAll(
                () -> assertEquals(expected, actual),
                () -> assertEquals(3, actual.getBody().getTotalPages()),
                () -> assertEquals(5, actual.getBody().getTotalElements())
        );
    }
}
