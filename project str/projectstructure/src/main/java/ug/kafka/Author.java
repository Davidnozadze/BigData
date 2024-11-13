package ug.kafka;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Author extends PanacheEntity {
    public String name;
    public String country;

    public Author() {} // Default constructor for JPA

    public Author(String name, String country) {
        this.name = name;
        this.country = country;
    }
}

