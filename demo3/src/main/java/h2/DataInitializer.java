package h2;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.annotation.PostConstruct;


@ApplicationScoped
public class DataInitializer {

    @PostConstruct
    @Transactional
    public void loadInitialData() {
        if (Book.count() == 0) {
            // Add initial books to the database
            new Book("The Great Gatsby", "F. Scott Fitzgerald").persist();
            new Book("To Kill a Mockingbird", "Harper Lee").persist();
            new Book("1984", "George Orwell").persist();
        }
    }
}
