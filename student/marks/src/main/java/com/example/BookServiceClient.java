package com.example;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


import java.util.List;
@RegisterRestClient(baseUri = "http://localhost:8080") // Adjust the URI as needed
@Path("/student")
public interface BookServiceClient {
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    List<h2.Student> searchByAuthor(@QueryParam("author") String author);
}