package com.newsmanagementsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("publisher_editor")
public class PublisherEditor extends User{
}
