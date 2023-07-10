package com.newsmanagementsystem.model;

import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "content")
public class Content extends BaseEntity{

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "scope")
    @Enumerated(EnumType.STRING)
    private ScopeEnum scopeEnum;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "publisher_editor_id")
    private PublisherEditor publisherEditor;

    public Content(Long id) {
        super(id);
    }
}
