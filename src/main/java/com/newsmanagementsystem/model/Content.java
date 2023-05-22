package com.newsmanagementsystem.model;

import com.newsmanagementsystem.model.enums.Category;
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
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private Date date;

    @Column(name = "scope")
    @Enumerated(EnumType.STRING)
    private Scope scope;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "publisher_editor_id")
    private PublisherEditor publisherEditor;
}
