package com.example;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;


@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    @Inject
    @RestClient
    BookServiceClient bookServiceClient;

    @GET
    public List<Author> getAllAuthors() {
        return Author.listAll();
    }

    @POST
    @Transactional
    public Response addAuthor(Author author) {
        if (author == null || author.name == null || author.country == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid author data")
                    .build();
        }
        author.persist();
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @GET
    @Path("/{id}")
    public Author getAuthorById(@PathParam("id") Long id) {
        Author author = Author.findById(id);
        if (author == null) {
            throw new WebApplicationException("Author not found", Response.Status.NOT_FOUND);
        }
        return author;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteAuthor(@PathParam("id") Long id) {
        boolean deleted = Author.deleteById(id);
        if (!deleted) {
            throw new WebApplicationException("Author not found", Response.Status.NOT_FOUND);
        }
        return Response.noContent().build();
    }


  // Assuming BookServiceClient is a REST client interface



    @GET
    @Path("/{id}/books")
    public List<Book> getBooksForAuthor(@PathParam("id") Long authorId) {
        System.out.println("Fetching books for author with ID: " + authorId);
        Author author = Author.findById(authorId);
        if (author == null) {
            System.out.println("Author not found");
            throw new WebApplicationException("Author not found", 404);
        }

        try {
            List<Book> books = bookServiceClient.searchByAuthor(author.name);
            System.out.println("Books fetched successfully");
            return books;
        } catch (Exception e) {
            System.err.println("Error while calling BookServiceClient: " + e.getMessage());
            throw new WebApplicationException("Error fetching books", 500);
        }
    }
}
