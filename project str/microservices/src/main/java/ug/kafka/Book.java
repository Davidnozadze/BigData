package ug.kafka;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Book extends PanacheEntity {
    public String title;
    public String author;

    public Book() {}

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
}

