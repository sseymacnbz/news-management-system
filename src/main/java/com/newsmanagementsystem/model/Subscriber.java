package com.newsmanagementsystem.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("subscriber")
public class Subscriber extends User{
}
