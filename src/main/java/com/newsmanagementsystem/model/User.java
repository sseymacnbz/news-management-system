package com.newsmanagementsystem.model;

import com.newsmanagementsystem.model.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserType type;

    @ManyToOne
    @JoinColumn(name = "viewer_id")
    private Viewer viewer;

}
