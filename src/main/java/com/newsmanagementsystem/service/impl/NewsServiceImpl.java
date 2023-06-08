package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.mapper.DisplayNewsMapper;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.model.enums.NewsTypeEnum;
import com.newsmanagementsystem.repository.NewsRepository;
import com.newsmanagementsystem.service.NewsService;
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


    @Override
    public ResponseEntity<HttpStatus> save(News news) {

        try{
            newsRepository.save(news);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Page<DisplayNewsResponse>> displayNewsForSubscriber(Pageable pageable) {

        try {
            Pageable pageableResponse = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
            List<News> isHeadlineAndPaid = newsRepository.findAllByIsHeadlineOrderByDateDesc(true);
            List<News> isNotHeadlineAndPaid = newsRepository.findAllByIsHeadlineOrderByDateDesc(false);
            List<News> allNews = Stream.concat(isHeadlineAndPaid.stream(), isNotHeadlineAndPaid.stream()).toList();

            int start = (int) pageableResponse.getOffset();
            int end = Math.min((start + pageableResponse.getPageSize()), allNews.size());
            List<News> pageContent = allNews.subList(start, end);

            return new ResponseEntity<>(new PageImpl<>(DisplayNewsMapper.INSTANCE.newsToDisplayNewsResponse(pageContent), pageableResponse, allNews.size()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Page<DisplayNewsResponse>> displayNewsForNonSubscriber(Pageable pageable) {

        Pageable pageableResponse = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), pageable.getSort()); // Page Requesti oluşturulur

        List<News> isHeadlineAndFree= newsRepository.findAllByIsHeadlineAndNewsTypeEnumOrderByDateDesc(true, NewsTypeEnum.FREE_NEWS); // manşet ve ücretsiz olanlar
        List<News> isNotHeadlineAndFree= newsRepository.findAllByIsHeadlineAndNewsTypeEnumOrderByDateDesc(false, NewsTypeEnum.FREE_NEWS); // manşet olmayan ve ücretsiz olanlar
        List<News> newsList = Stream.concat(isHeadlineAndFree.stream(), isNotHeadlineAndFree.stream()).toList(); // Yukarıdaki iki liste birleştirilir

        // Listeyi, Paging listeye çevrilmek için gerekli işlemleri
        int start = (int) pageableResponse.getOffset();
        int end = Math.min((start + pageableResponse.getPageSize()), newsList.size());
        List<News> pageContent = newsList.subList(start, end);

        return new ResponseEntity<>(new PageImpl<>(DisplayNewsMapper.INSTANCE.newsToDisplayNewsResponse(pageContent), pageableResponse, newsList.size()), HttpStatus.OK);
    }

    @Override
    public News findById(Long newsId) {
        return newsRepository.getReferenceById(newsId);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(Long newsId) {
        try{
            newsRepository.deleteById(newsId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
