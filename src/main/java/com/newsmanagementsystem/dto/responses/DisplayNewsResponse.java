package com.newsmanagementsystem.dto.responses;

import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.NewsTypeEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayNewsResponse {
    private LocalDate date;
    private List<ScopeEnum> scopeEnum;
    private CategoryEnum categoryEnum;
    private Boolean isHeadline;
    private String title;
    private String text;
    private NewsTypeEnum newsTypeEnum;
}
