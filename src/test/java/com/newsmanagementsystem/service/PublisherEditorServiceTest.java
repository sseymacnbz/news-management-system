package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.dto.responses.DisplayContentsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.PublisherEditorNotFoundException;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;
import com.newsmanagementsystem.service.impl.ContentServiceImpl;
import com.newsmanagementsystem.service.impl.PublisherEditorServiceImpl;
import com.newsmanagementsystem.service.impl.UserServiceImpl;
import org.checkerframework.checker.units.qual.C;
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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherEditorServiceTest {

    @InjectMocks
    PublisherEditorServiceImpl publisherEditorService;
    @Mock
    UserServiceImpl userService;
    @Mock
    ContentServiceImpl contentService;


    @Test
    @DisplayName("createContent test")
    void create_shouldCreateContent(){

        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        Content content = new Content();
        assertDoesNotThrow(() -> contentService.save(content));
    }

    @Test
    @DisplayName("createContentThrowException test")
    void throw_shouldThrowPublisherEditorNotFoundException(){

        CreateContentRequest createContentRequest = new CreateContentRequest();
        createContentRequest.setPublisherEditorId(7L);

        PublisherEditorNotFoundException expected = new PublisherEditorNotFoundException(7L);

        doThrow(expected).when(userService).existsPublisherEditorById(7L);

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

        doReturn(true).when(userService).existsPublisherEditorById(3L);
        List<Content> contentList = new ArrayList<>();
        ResponseEntity<List<Content>> expectedList = new ResponseEntity<>(contentList,HttpStatus.OK);
        doReturn(expectedList).when(contentService).findAllByPublisherEditorId(3L);
        doThrow(PublisherEditorNotFoundException.class).when(userService).existsPublisherEditorById(100L);

        assertAll(
                () -> assertDoesNotThrow(() -> publisherEditorService.displayContents(pageableResponse,3L)),
                () -> assertThrows(PublisherEditorNotFoundException.class, () -> publisherEditorService.displayContents(pageableResponse,100L))
        );
    }
}
