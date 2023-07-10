package com.newsmanagementsystem.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePublisherEditorRequest {
    private Long mainEditorId;
    private String name;
    private String surname;
    private String email;
    private String password;
}
