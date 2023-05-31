package com.newsmanagementsystem.dto.requests;

import com.newsmanagementsystem.model.Content;
import com.newsmanagementsystem.model.MainEditor;
import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.NewsTypeEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsRequest {
    private LocalDate date;
    private ScopeEnum scopeEnum;
    private CategoryEnum categoryEnum;
    private Boolean isHeadline;
    private String title;
    private String text;
    private NewsTypeEnum newsTypeEnum;
    private MainEditor mainEditor;
    private Content content;
}
