package com.newsmanagementsystem.dto.requests;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
}
