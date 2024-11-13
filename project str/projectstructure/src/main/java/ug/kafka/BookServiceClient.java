package ug.kafka;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "book-service")
@Path("/books")
public interface BookServiceClient {

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    List<Book> searchByAuthor(@QueryParam("author") String author);
}

