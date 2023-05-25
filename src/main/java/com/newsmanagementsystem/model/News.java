package com.newsmanagementsystem.model;

import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.NewsTypeEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;
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
public class News{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "scope")
    @Enumerated(EnumType.STRING)
    private ScopeEnum scopeEnum;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum;

    @Column(name = "isHeadline")
    private Boolean isHeadline;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "newsType")
    @Enumerated(EnumType.STRING)
    private NewsTypeEnum newsTypeEnum;

    @ManyToOne
    @JoinColumn(name = "main_editor_id")
    private MainEditor mainEditor;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

}
