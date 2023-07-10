package com.newsmanagementsystem.service;

import com.newsmanagementsystem.dto.requests.CreateContentRequest;
import com.newsmanagementsystem.dto.requests.CreatePublisherEditorRequest;
import com.newsmanagementsystem.dto.responses.DisplayContentsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.PublisherEditorNotFoundException;
import com.newsmanagementsystem.mapper.ContentMapper;
import com.newsmanagementsystem.mapper.PublisherEditorMapper;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.service.impl.ContentServiceImpl;
import com.newsmanagementsystem.service.impl.PublisherEditorServiceImpl;
import com.newsmanagementsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
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
class PublisherEditorServiceTest {

    @Spy
    @InjectMocks
    PublisherEditorServiceImpl publisherEditorService;
    @Mock
    UserServiceImpl userService;
    @Mock
    ContentServiceImpl contentService;


    @Test
    @DisplayName("createContent test")
    void create_shouldCreateContent(){

        CreateContentRequest createContentRequest = new CreateContentRequest();
        createContentRequest.setPublisherEditorId(2L);

        doReturn(true).when(userService).existsPublisherEditorById(2L);
        Content content = ContentMapper.INSTANCE.createContentRequestToContent(createContentRequest);
        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(expected).when(contentService).save(content);

        assertDoesNotThrow(() -> publisherEditorService.createContent(createContentRequest));
        createContentRequest.setPublisherEditorId(3L);
        assertThrows(PublisherEditorNotFoundException.class,() -> publisherEditorService.createContent(createContentRequest));
    }

    @Test
    @DisplayName("createPublisherEditor test")
    void create_shouldCreatePublisherEditor(){
        CreatePublisherEditorRequest createPublisherEditorRequest = new CreatePublisherEditorRequest();
        createPublisherEditorRequest.setMainEditorId(1L);

        PublisherEditor publisherEditor = PublisherEditorMapper.INSTANCE.createPublisherEditorRequestToPublisherEditor(createPublisherEditorRequest);
        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(expected).when(userService).createPublisherEditor(publisherEditor);

        assertDoesNotThrow(() -> publisherEditorService.createPublisherEditor(createPublisherEditorRequest));
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

        assertDoesNotThrow(() -> publisherEditorService.displayContents(pageableResponse,3L));
        assertThrows(PublisherEditorNotFoundException.class, () -> publisherEditorService.displayContents(pageableResponse,100L));

    }
}
