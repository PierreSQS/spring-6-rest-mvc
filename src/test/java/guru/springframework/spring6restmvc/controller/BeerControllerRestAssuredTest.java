package guru.springframework.spring6restmvc.controller;

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
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static io.restassured.RestAssured.given;

/**
 * Created by Pierrot on 04-07-2024.
 */
@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = {BeerControllerRestAssuredTest.TestSecurityConfig.class})
@ComponentScan(basePackages = {"guru.springframework.spring6restmvc"})
class BeerControllerRestAssuredTest {

    @Configuration
    public static class TestSecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/v1/beer").permitAll()
                    .anyRequest().authenticated());

            return httpSecurity.build();
        }

    }

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
