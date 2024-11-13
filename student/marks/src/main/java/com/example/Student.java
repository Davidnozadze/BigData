package h2;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Student extends PanacheEntity {

    public String title;
    public String author;

    // Default constructor for JPA
    public Student() {}

    // Constructor for creating Book objects
    public Student(String title, String author) {
        this.title = title;
        this.author = author;
    }
}