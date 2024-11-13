package com.example;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Book extends PanacheEntity {
    public String title;
    public String author;

    // Default constructor for JPA
    public Book() {}

    // Constructor for creating Book objects
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
}