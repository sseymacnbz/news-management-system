package com.newsmanagementsystem.service;

import com.newsmanagementsystem.exceptionhandler.exceptiontypes.ContentNotFoundException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.PublisherEditorNotFoundException;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.repository.ContentRepository;
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
class ContentServiceTest {
    @Mock
    ContentRepository contentRepository;
    @Mock
    NewsServiceImpl newsService;
    @Mock
    UserServiceImpl userService;
    @Spy
    @InjectMocks
    ContentServiceImpl contentService;

    @Test
    @DisplayName("getContentsThatIsNews test")
    void get_shouldGetContentsThatIsNews(){
        Pageable pageableResponse = PageRequest.of(0, 2);

        List<Long> newsContentList = new ArrayList<>();
        newsContentList.add(1L);
        newsContentList.add(2L);
        ResponseEntity<List<Long>> listContentsResponse = new ResponseEntity<>(newsContentList,HttpStatus.OK);

        doReturn(listContentsResponse).when(newsService).newsContents();
        assertNotNull(newsContentList);

        Optional<Content> optionalContent = Optional.of(new Content(1L));
        //given(contentRepository.findById(anyLong())).willReturn(optionalContent);

        doReturn(optionalContent).when(contentRepository).findById(anyLong());

        assertDoesNotThrow(() -> contentService.getContentsThatIsNews(pageableResponse));

    }

    @Test
    @DisplayName("getContentsThatIsNotNews test")
    void get_shouldGetContentsThatIsNotNews(){
        Pageable pageableResponse = PageRequest.of(0, 2);

        List<Long> longList = new ArrayList<>();
        longList.add(1L);
        ResponseEntity<List<Long>> expected = new ResponseEntity<>(longList, HttpStatus.OK);
        doReturn(expected).when(newsService).newsContents();

        List<Content> contentList = new ArrayList<>();
        contentList.add(new Content(1L));

        doReturn(contentList).when(contentRepository).findAll();

        assertNotNull(contentList);

        Optional<Content> optionalContent = Optional.of(new Content(1L));

        doReturn(optionalContent).when(contentRepository).findById(anyLong());

        assertDoesNotThrow(() -> contentService.getContentsThatIsNotNews(pageableResponse));

    }

    @Test
    @DisplayName("save test")
    @Transactional
    @Rollback
    void save_shouldSaveContent(){
        Content content = new Content();
        doReturn(content).when(contentRepository).save(content);
        assertDoesNotThrow(() -> contentService.save(content));
    }

    @Test
    @DisplayName("delete test")
    @Transactional
    @Rollback
    void delete_shouldDeleteContent(){
        ResponseEntity<HttpStatus> expected  = new ResponseEntity<>(HttpStatus.OK);
        List<Content> contentList = new ArrayList<>();
        contentList.add(new Content(1L));

        doReturn(true).when(contentRepository).existsContentById(1L);

        doReturn(expected).when(newsService).deleteNewsByContents(contentList);

        doNothing().when(contentRepository).deleteById(1L);

        assertDoesNotThrow(() -> contentService.delete(1L));
        assertThrows(ContentNotFoundException.class, () -> contentService.delete(3L));

    }

    @Test
    @DisplayName("deleteAllByPublisherEditorId test")
    @Transactional
    @Rollback
    void delete_shouldDeleteAllByPublisherEditorId(){
        doReturn(true).when(userService).existsPublisherEditorById(1L);
        assertDoesNotThrow(() -> userService.existsPublisherEditorById(1L));

        assertThrows(PublisherEditorNotFoundException.class, () -> contentService.deleteAllByPublisherEditorId(2L));

        assertDoesNotThrow(() -> contentService.deleteAllByPublisherEditorId(1L));

        List<Content> contentList = new ArrayList<>();
        contentList.add(new Content(1L));

        doReturn(contentList).when(contentRepository).findAllByPublisherEditorId(1L);
        assertDoesNotThrow(() -> contentRepository.findAllByPublisherEditorId(1L));

    }

    @Test
    @DisplayName("findAllByPublisherEditorId test")
    @Transactional
    void find_shouldFindAllByPublisherEditorId(){

        doReturn(true).when(userService).existsPublisherEditorById(2L);
        List<Content> contentList = new ArrayList<>();

        doReturn(contentList).when(contentRepository).findAllByPublisherEditorId(2L);

        assertThrows(PublisherEditorNotFoundException.class, () -> contentService.findAllByPublisherEditorId(3L));
        assertDoesNotThrow(() -> contentService.findAllByPublisherEditorId(2L));

    }

    @Test
    @DisplayName("isContentExist test")
    @Transactional
    void find_shouldFindIsContentExist(){
        doReturn(true).when(contentRepository).existsContentById(2L);
        assertDoesNotThrow(() -> contentService.isContentExist(2L));
    }
}
