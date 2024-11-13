package h2;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Student extends PanacheEntity {

    public String name;
    public String subject;

    // Default constructor for JPA
    public Student() {}

    // Constructor for creating Book objects
    public Student(String name, String subject) {
        this.name = name;
        this.subject = subject;
    }
}