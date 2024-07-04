package guru.springframework.spring6restmvc.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

/**
 * Created by Pierrot on 04-07-2024.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeerControllerRestAssuredTest {

    @LocalServerPort
    Integer localPort;

    @BeforeEach
    void setUp() {
        RestAssured.port = localPort;
    }

    @Test
    void testListBeers() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/v1/beer")
                .then()
                .assertThat().statusCode(200);
    }
}
