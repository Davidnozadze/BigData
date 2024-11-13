package com.example;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Author extends PanacheEntity {
    public String name;
    public String country;

    // Default constructor for JPA
    public Author() {}

    // Constructor for creating Author objects
    public Author(String name, String country) {
        this.name = name;
        this.country = country;
    }
}