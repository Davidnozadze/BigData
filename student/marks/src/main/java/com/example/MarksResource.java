package com.example;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;


@Path("/marks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarksResource {
    @Inject
    @RestClient
    BookServiceClient StudentServiceClient;

    @GET
    public List<Marks> getAllMarks() {
        return Marks.listAll();
    }

    @POST
    @Transactional
    public Response addMarks(Marks marks) {
        if (marks == null || marks.name == null || marks.country == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid author data")
                    .build();
        }
        marks.persist();
        return Response.status(Response.Status.CREATED).entity(marks).build();
    }

    @GET
    @Path("/{id}")
    public Marks getAuthorById(@PathParam("id") Long id) {
        Marks author = Marks.findById(id);
        if (author == null) {
            throw new WebApplicationException("Author not found", Response.Status.NOT_FOUND);
        }
        return author;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteMarks(@PathParam("id") Long id) {
        boolean deleted = Marks.deleteById(id);
        if (!deleted) {
            throw new WebApplicationException("Author not found", Response.Status.NOT_FOUND);
        }
        return Response.noContent().build();
    }


  // Assuming BookServiceClient is a REST client interface



    @GET
    @Path("/{id}/student")
    public List<h2.Student> getStudentsForMarks(@PathParam("id") Long authorId) {
        System.out.println("Fetching books for author with ID: " + authorId);
        Marks author = Marks.findById(authorId);
        if (author == null) {
            System.out.println("Author not found");
            throw new WebApplicationException("Author not found", 404);
        }

        try {
            List<h2.Student> books = StudentServiceClient.searchByAuthor(Marks.name);
            System.out.println("Books fetched successfully");
            return books;
        } catch (Exception e) {
            System.err.println("Error while calling BookServiceClient: " + e.getMessage());
            throw new WebApplicationException("Error fetching books", 500);
        }
    }
}
