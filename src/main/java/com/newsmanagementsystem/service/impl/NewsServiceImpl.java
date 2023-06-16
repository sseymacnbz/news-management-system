package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.ContentsNotFoundException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.NewsNotFoundException;
import com.newsmanagementsystem.mapper.DisplayNewsMapper;
import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.model.enums.NewsTypeEnum;
import com.newsmanagementsystem.repository.NewsRepository;
import com.newsmanagementsystem.service.NewsService;
import com.newsmanagementsystem.utilities.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private LogUtil logUtil;

    private static final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Override
    public ResponseEntity<List<Long>> newsContents() {
        try {
            return ResponseEntity.ok(newsRepository.newsContents());
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> save(News news) {

        try{
            newsRepository.save(news);
            log.info(logUtil.getMessage(Thread.currentThread().getStackTrace()[1].getMethodName(),"news.created",HttpStatus.CREATED.value()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Page<DisplayNewsResponse>> displayNewsForSubscriber(Pageable pageable, Long userId) {

        Pageable pageableResponse = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        List<News> isHeadlineAndPaid = newsRepository.findAllByIsHeadlineOrderByDateDesc(true);
        List<News> isNotHeadlineAndPaid = newsRepository.findAllByIsHeadlineOrderByDateDesc(false);
        List<News> allNews = Stream.concat(isHeadlineAndPaid.stream(), isNotHeadlineAndPaid.stream()).toList();

        int start = (int) pageableResponse.getOffset();
        int end = Math.min((start + pageableResponse.getPageSize()), allNews.size());
        List<News> pageContent = allNews.subList(start, end);
        pageContent.stream().forEach(news -> log.info(logUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"news.display",news.getId(),HttpStatus.OK.value())));
        log.info(logUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"user.display.news",userId,HttpStatus.OK.value()));
        return new ResponseEntity<>(new PageImpl<>(DisplayNewsMapper.INSTANCE.newsToDisplayNewsResponse(pageContent), pageableResponse, allNews.size()), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Page<DisplayNewsResponse>> displayNewsForNonSubscriber(Pageable pageable, Long userId) {

        Pageable pageableResponse = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), pageable.getSort()); // Page Requesti oluşturulur

        List<News> isHeadlineAndFree= newsRepository.findAllByIsHeadlineAndNewsTypeEnumOrderByDateDesc(true, NewsTypeEnum.FREE_NEWS); // manşet ve ücretsiz olanlar
        List<News> isNotHeadlineAndFree= newsRepository.findAllByIsHeadlineAndNewsTypeEnumOrderByDateDesc(false, NewsTypeEnum.FREE_NEWS); // manşet olmayan ve ücretsiz olanlar
        List<News> newsList = Stream.concat(isHeadlineAndFree.stream(), isNotHeadlineAndFree.stream()).toList(); // Yukarıdaki iki liste birleştirilir

        // Listeyi, Paging listeye çevrilmek için gerekli işlemleri
        int start = (int) pageableResponse.getOffset();
        int end = Math.min((start + pageableResponse.getPageSize()), newsList.size());
        List<News> pageContent = newsList.subList(start, end);

        pageContent.stream().forEach(news -> log.info(logUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"news.display",news.getId(),HttpStatus.OK.value())));
        log.info(logUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"user.display.news",userId,HttpStatus.OK.value()));
        return new ResponseEntity<>(new PageImpl<>(DisplayNewsMapper.INSTANCE.newsToDisplayNewsResponse(pageContent), pageableResponse, newsList.size()), HttpStatus.OK);
    }
    @Override
    public boolean isNewsExist(Long newsId) {
        return newsRepository.existsNewsById(newsId) ;
    }

    @Override
    public News findById(Long newsId) {
        return newsRepository.getReferenceById(newsId);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long newsId) {
        if(isNewsExist(newsId)){
            try{
                newsRepository.delete(newsRepository.getReferenceById(newsId));
                log.info(logUtil.getMessageWithId(Thread.currentThread().getStackTrace()[1].getMethodName(),"news.deleted",newsId,HttpStatus.OK.value()));
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else throw new NewsNotFoundException(newsId);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteNewsByContents(List<Content> contentList) {

        try{
            contentList.stream().forEach(content -> {
                newsRepository.findByContentId(content.getId()).stream().forEach(news -> delete(news.getId()));
            });
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
