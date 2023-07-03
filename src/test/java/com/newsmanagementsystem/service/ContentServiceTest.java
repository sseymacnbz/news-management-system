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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ContentServiceTest {
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
        List<Content> contentList = new ArrayList<>();
        ResponseEntity<Page<Content>> expected =
                new ResponseEntity<>(new PageImpl<>(contentList,pageableResponse,5), HttpStatus.OK);

        doReturn(expected).when(contentService).getContentsThatIsNews(pageableResponse);
        assertDoesNotThrow(() -> contentService.getContentsThatIsNews(pageableResponse));
    }

    @Test
    @DisplayName("getContentsThatIsNotNews test")
    void get_shouldGetContentsThatIsNotNews(){
        Pageable pageableResponse = PageRequest.of(0, 2);
        List<Content> contentList = new ArrayList<>();
        ResponseEntity<Page<Content>> expected =
                new ResponseEntity<>(new PageImpl<>(contentList,pageableResponse,5), HttpStatus.OK);

        doReturn(expected).when(contentService).getContentsThatIsNotNews(pageableResponse);
        assertDoesNotThrow(() -> contentService.getContentsThatIsNotNews(pageableResponse));
    }
    @Test
    @DisplayName("save test")
    @Transactional
    @Rollback
    void save_shouldSaveContent(){
        Content content = new Content();
        doReturn(content).when(contentRepository).save(content);

        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.CREATED);
        doReturn(expected).when(contentService).save(content);

        assertDoesNotThrow(() -> contentService.save(content));
    }

    @Test
    @DisplayName("delete test")
    @Transactional
    @Rollback
    void delete_shouldDeleteContent(){
        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.OK);
        List<Content> contentList = new ArrayList<>();
        contentList.add(new Content(2L));
        doReturn(expected).when(newsService).deleteNewsByContents(contentList);

        doNothing().when(contentRepository).deleteById(2L);

        doReturn(expected).when(contentService).delete(2L);
        doThrow(ContentNotFoundException.class).when(contentService).delete(100L);
        assertDoesNotThrow(() -> contentService.delete(2L));
    }

    @Test
    @DisplayName("deleteAllByPublisherEditorId test")
    @Transactional
    @Rollback
    void delete_shouldDeleteAllByPublisherEditorId(){
        ResponseEntity<HttpStatus> expected = new ResponseEntity<>(HttpStatus.OK);
        doReturn(true).when(userService).existsPublisherEditorById(2L);

        List<Content> contentList = new ArrayList<>();
        doReturn(contentList).when(contentRepository).findAllByPublisherEditorId(2L);

        doReturn(expected).when(contentService).deleteAllByPublisherEditorId(2L);

        assertDoesNotThrow(() -> contentService.deleteAllByPublisherEditorId(2L));
    }

    @Test
    @DisplayName("findAllByPublisherEditorId test")
    @Transactional
    void find_shouldFindAllByPublisherEditorId(){
        ResponseEntity<List<Content>> expected = new ResponseEntity<>(HttpStatus.OK);

        doReturn(true).when(userService).existsPublisherEditorById(2L);

        doReturn(expected).when(contentService).findAllByPublisherEditorId(2L);
        doThrow(PublisherEditorNotFoundException.class).when(contentService).findAllByPublisherEditorId(3L);

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
