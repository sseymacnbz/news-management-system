package com.newsmanagementsystem.model;

import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.NewsTypeEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "news")
public class News extends BaseEntity{

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "scope")
    @Enumerated(EnumType.STRING)
    private List<ScopeEnum> scopeEnum;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "main_editor_id")
    private MainEditor mainEditor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    private Content content;

}
