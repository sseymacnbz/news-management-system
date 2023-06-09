package com.newsmanagementsystem.model;

import com.newsmanagementsystem.model.enums.CategoryEnum;
import com.newsmanagementsystem.model.enums.ScopeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "content")
public class Content{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    public Content(Long id){
        this.id = id;
    }

}
