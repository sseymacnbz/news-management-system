package com.newsmanagementsystem.dto.requests;

import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.NewsTypeEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsRequest {

    private ScopeEnum scopeEnum;
    private CategoryEnum categoryEnum;
    private Boolean isHeadline;
    private String title;
    private String text;
    private NewsTypeEnum newsTypeEnum;
    private Long mainEditorId;
    private Long contentId;
}
