package com.newsmanagementsystem.dto.requests;

import lombok.Data;

@Data
public class CreateUserRequest {

    private Long mainEditorId;
    private String name;
    private String surname;
    private String email;
    private String password;
}
