package com.newsmanagementsystem.repository;

import com.newsmanagementsystem.model.News;
import com.newsmanagementsystem.model.enums.NewsTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>{

    List<News> findAllByIsHeadlineAndNewsTypeEnumOrderByDateDesc(boolean isHeadline, NewsTypeEnum newsTypeEnum);

    List<News> findAllByIsHeadlineOrderByDateDesc(boolean isHeadline);

    List<News> findByContentId(Long contentId);

    boolean existsNewsById(Long id);

}
