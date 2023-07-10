package com.newsmanagementsystem.dto.requests;

import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateContentRequest {
    private LocalDate date;
    private ScopeEnum scopeEnum;
    private CategoryEnum categoryEnum;
    private String title;
    private String text;
    private Long publisherEditorId;
}
