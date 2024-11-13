package h2;


import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class BookResourceTest {

    @Test
    public void testGetAllBooksEndpoint() {
        given()
                .when().get("/books")
                .then()
                .statusCode(200)
                .body("size()", is(greaterThanOrEqualTo(0)));
    }

    @Test
    public void testAddBookEndpoint() {
        Book book = new Book();
        book.title = "Test Book";


        given()
                .contentType("application/json")
                .body(book)
                .when().post("/books")
                .then()
                .statusCode(201);
    }

    @Test
    public void testGetBookByIdEndpoint() {
        given()
                .pathParam("id", 1)
                .when().get("/books/{id}")
                .then()
                .statusCode(200)
                .body("title", notNullValue());
    }

    @Test
    public void testGetBookWithInvalidId() {
        given()
                .pathParam("id", 999)
                .when().get("/books/{id}")
                .then()
                .statusCode(404)
                .body("details", containsString("Book not found"));
    }

    @Test
    public void testDeleteBookEndpoint() {
        given()
                .pathParam("id", 1)
                .when().delete("/books/{id}")
                .then()
                .statusCode(204);
    }
}