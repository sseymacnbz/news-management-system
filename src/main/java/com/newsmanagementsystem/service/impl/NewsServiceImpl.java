package com.newsmanagementsystem.service.impl;

import com.newsmanagementsystem.dto.requests.UpdateNewsRequest;
import com.newsmanagementsystem.dto.responses.DisplayNewsResponse;
import com.newsmanagementsystem.mapper.DisplayNewsMapper;
import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.model.enums.NewsTypeEnum;
import com.newsmanagementsystem.repository.NewsRepository;
import com.newsmanagementsystem.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ResponseEntity<List<DisplayNewsResponse>> displayNewsForSubscriber() {

        try{
            List<News> newsList = newsRepository.findAllByOrderByDateDesc(); // Tüm news'leri tarihi azalan şekilde getirir

            List<News> paidNewsList = newsList.stream()
                    .filter(news -> news.getNewsTypeEnum().equals(NewsTypeEnum.PAID_NEWS) && news.getIsHeadline()).toList(); // haberlerdeki ücretli ve manşet olanlari sırayla filtreleyip usersNewsList'e atar

            paidNewsList = Stream.concat(paidNewsList.stream(), newsList.stream()
                                                                           .filter(news -> news.getNewsTypeEnum().equals(NewsTypeEnum.PAID_NEWS) && !news.getIsHeadline()))
                                                                           .parallel().toList(); // usersNewsList ile ücretli ve manşet olmayan haberleri concat eder.

            List<DisplayNewsResponse> displayNewsResponseList = new ArrayList<>(); // en son gösterilecek haberlerin listesi

            paidNewsList.stream().map(DisplayNewsMapper.INSTANCE::newsToDisplayNewsResponse).forEach(displayNewsResponseList::add); // Mapper'dan geçen ücretli haberler displayNewsResponseList'e ekleniyor

            List<DisplayNewsResponse> freeNewsList = displayNewsForNonSubscriber().getBody(); // ücretsiz haberler getiriliyor

            if(freeNewsList != null){ // ücretsiz haberler null dönmezse en sona ücretsizler de eklenecek.
                displayNewsResponseList = Stream.concat(displayNewsResponseList.stream(), freeNewsList.stream()).parallel().toList(); // displayNewsResponseList'in ardina ücretsiz olanlar da ekleniyor
            }

            return new ResponseEntity<>(displayNewsResponseList, HttpStatus.OK);

        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<DisplayNewsResponse>> displayNewsForNonSubscriber() {

        try{
            List<News> newsList = newsRepository.findAllByOrderByDateDesc();
            List<News> usersNewsList = newsList.stream()
                    .filter(news -> news.getNewsTypeEnum().equals(NewsTypeEnum.FREE_NEWS) && news.getIsHeadline()).toList();
            usersNewsList = Stream.concat(usersNewsList.stream(), newsList.stream().filter(news -> news.getNewsTypeEnum().equals(NewsTypeEnum.FREE_NEWS) && !news.getIsHeadline())).parallel().toList();

            List<DisplayNewsResponse> displayNewsResponseList = new ArrayList<>();

            usersNewsList.stream().map(DisplayNewsMapper.INSTANCE::newsToDisplayNewsResponse).forEach(displayNewsResponseList::add);

            return new ResponseEntity<>(displayNewsResponseList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
