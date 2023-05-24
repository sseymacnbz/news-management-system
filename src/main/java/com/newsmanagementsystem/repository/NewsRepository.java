package com.newsmanagementsystem.repository;

import com.newsmanagementsystem.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends BaseRepository<News, Integer> {
}
