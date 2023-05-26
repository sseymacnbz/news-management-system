package com.newsmanagementsystem.dto.requests;

import com.newsmanagementsystem.model.PublisherEditor;
import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;

import java.time.LocalDateTime;


public class CreateContentRequest {
    private LocalDateTime date = LocalDateTime.now();
    private ScopeEnum scopeEnum;
    private CategoryEnum categoryEnum;
    private String title;
    private String text;
    private PublisherEditor publisherEditor;
}
