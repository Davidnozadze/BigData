package h2;

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
    @Transactional
    public Response addBook(Book book) {
        if (book == null || book.title == null || book.author == null) {
            throw new WebApplicationException("Book data is incomplete", Response.Status.BAD_REQUEST);
        }
        book.persist();
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") Long id) {
        Book book = Book.findById(id);
        if (book == null) {
            throw new WebApplicationException("Book with ID " + id + " not found", Response.Status.NOT_FOUND);
        }
        return Response.ok(book).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteBook(@PathParam("id") Long id) {
        boolean deleted = Book.deleteById(id);
        if (!deleted) {
            throw new WebApplicationException("Book with ID " + id + " not found", Response.Status.NOT_FOUND);
        }
        return Response.noContent().build();
    }

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
    @Transactional
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
