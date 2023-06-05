package com.newsmanagementsystem.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignRequest {
    Long mainEditorId;
    Long userId;
}
