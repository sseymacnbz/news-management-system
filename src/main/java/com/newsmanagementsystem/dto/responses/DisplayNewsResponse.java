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
    LocalDate date;
    List<ScopeEnum> scopeEnum;
    CategoryEnum categoryEnum;
    Boolean isHeadline;
    String title;
    String text;
    NewsTypeEnum newsTypeEnum;
}
