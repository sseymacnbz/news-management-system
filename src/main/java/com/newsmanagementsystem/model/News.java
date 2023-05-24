package com.newsmanagementsystem.model;

import com.newsmanagementsystem.model.enums.Category;
import com.newsmanagementsystem.model.enums.NewsType;
import com.newsmanagementsystem.model.enums.Scope;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "news")
public class News extends BaseModel{

    @Column(name = "date")
    private Date date;

    @Column(name = "scope")
    @Enumerated(EnumType.STRING)
    private Scope scope;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "isHeadline")
    private Boolean isHeadline;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "newsType")
    @Enumerated(EnumType.STRING)
    private NewsType newsType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

}
