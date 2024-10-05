package guru.springframework.spring6restmvc.controller;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

/**
 * Created by Pierrot on 04-10-2024.
 */
@ActiveProfiles("test") // to overcome the Spring Securtity config (See the SpringSecConfig class !!!
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({BeerControllerRestAssuredTest.TestConfig.class})
@ComponentScan(basePackages = "guru.springframework.spring6restmvc")
class BeerControllerRestAssuredTest {

    @Configuration
    public static class TestConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());

            return httpSecurity.build();
        }
    }

    @LocalServerPort
    Integer localPort;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = localPort;
    }

    @Test
    void testListBeers() {
        OpenApiValidationFilter openApiValidationFilter = new OpenApiValidationFilter(OpenApiInteractionValidator
                .createForSpecificationUrl("oa3.yml")
                .build());

        given().contentType(ContentType.JSON)
                .when()
                .filter(openApiValidationFilter)
                .get("/api/v1/beer")
                .then()
                .assertThat().statusCode(200);
    }
}
