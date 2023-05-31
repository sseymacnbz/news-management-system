package com.newsmanagementsystem.dto.requests;

import lombok.*;


@Data
public class CreatePublicUserRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Boolean isSubscribe = false; // Buna bi bak
}
