package ug.kafka;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @GET
    public List<Book> getAllBooks() {
        return Book.listAll();
    }

    @POST
    @Transactional // Add this annotation to ensure a transaction is active
    public void addBook(Book book) {
        book.persist();
    }

    @GET
    @Path("/{id}")
    public Book getBookById(@PathParam("id") Long id) {
        return Book.findById(id);
    }

    @DELETE
    @Path("/{id}")
    @Transactional // Add this annotation to ensure a transaction is active
    public void deleteBook(@PathParam("id") Long id) {
        Book.deleteById(id);
    }
    // Search for books by author
    @GET
    @Path("/search")
    public List<Book> searchByAuthor(@QueryParam("author") String author) {
        if (author == null || author.isEmpty()) {
            throw new WebApplicationException("Author parameter is required", Response.Status.BAD_REQUEST);
        }
        return Book.list("author", author);
    }

    @PUT
    @Path("/{id}")
    @Transactional // Ensure this operation is within a transaction
    public Response updateBook(@PathParam("id") Long id, Book updatedBook) {
        Book existingBook = Book.findById(id);
        if (existingBook == null) {
            throw new WebApplicationException("Book with ID " + id + " not found", Response.Status.NOT_FOUND);
        }

        // Update fields
        existingBook.title = updatedBook.title;
        existingBook.author = updatedBook.author;
        // Add other fields as needed

        existingBook.persist();
        return Response.ok(existingBook).build();
    }


}