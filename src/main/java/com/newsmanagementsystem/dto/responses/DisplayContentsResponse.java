package com.newsmanagementsystem.dto.responses;

import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayContentsResponse {

    private CategoryEnum categoryEnum;
    private LocalDate date;
    private ScopeEnum scopeEnum;
    private String text;
    private String title;

}
