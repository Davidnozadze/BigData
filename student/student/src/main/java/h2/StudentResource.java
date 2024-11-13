package h2;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/Student")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    @GET
    public List<Student> getAllStudents() {
        return Student.listAll();
    }

    @POST
    @Transactional
    public Response addStudent(Student student) {
        if (student == null || student.name == null || student.subject == null) {
            throw new WebApplicationException("Book data is incomplete", Response.Status.BAD_REQUEST);
        }
        student.persist();
        return Response.status(Response.Status.CREATED).entity(student).build();
    }

    @GET
    @Path("/{id}")
    public Response getStudentById(@PathParam("id") Long id) {
        Student student = Student.findById(id);
        if (student == null) {
            throw new WebApplicationException("Book with ID " + id + " not found", Response.Status.NOT_FOUND);
        }
        return Response.ok(student).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteStudent(@PathParam("id") Long id) {
        boolean deleted = Student.deleteById(id);
        if (!deleted) {
            throw new WebApplicationException("Book with ID " + id + " not found", Response.Status.NOT_FOUND);
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/search")
    public List<Student> searchByStudent(@QueryParam("author") String author) {
        if (author == null || author.isEmpty()) {
            throw new WebApplicationException("Author parameter is required", Response.Status.BAD_REQUEST);
        }
        return Student.list("author", author);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateStudent(@PathParam("id") Long id, Student updatedBook) {
        Student existingStudent = Student.findById(id);
        if (existingStudent == null) {
            throw new WebApplicationException("Student with ID " + id + " not found", Response.Status.NOT_FOUND);
        }

        // Update fields
        existingStudent.name = updatedBook.name;
        existingStudent.subject = updatedBook.subject;
        // Add other fields as needed

        existingStudent.persist();
        return Response.ok(existingStudent).build();
    }
}
