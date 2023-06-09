package com.newsmanagementsystem.repository;

import com.newsmanagementsystem.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long>{
    List<Content> findAllByPublisherEditorId(Long publisherEditorId);
}
