package com.newsmanagementsystem.model;

import com.newsmanagementsystem.model.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user_")
public class User extends BaseModel{

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_Subscribe")
    private Boolean isSubscribe;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserType type;

    @OneToMany(mappedBy = "user")
    private List<News> news;

    @OneToMany(mappedBy = "user")
    private List<Content> contents;



}
