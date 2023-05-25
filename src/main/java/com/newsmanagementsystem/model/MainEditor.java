package com.newsmanagementsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("main_editor")
public class MainEditor extends User{

}
