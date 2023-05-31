package com.newsmanagementsystem.dto.requests;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CreatePublicUserRequest {
    private String name;
    private String surname;
    private String email;
    private String password;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Boolean isSubscribe = false;
}
