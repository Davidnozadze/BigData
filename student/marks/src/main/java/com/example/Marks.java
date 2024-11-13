package com.example;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Marks extends PanacheEntity {
    public static String name;
    public String country;

    // Default constructor for JPA
    public Marks() {}

    // Constructor for creating Author objects
    public Marks(String name, String country) {
        this.name = name;
        this.country = country;
    }
}