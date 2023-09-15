package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JT Implementation
 * Implemented by Pierrot 15.09.2023
 *
 * This should be the proper IT in my sens!!!!!
 * At the moment the testEmptyList()-test doesn't work
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeerControllerPierreIT {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BeerRepository beerRepo;

    @Test
    void testListBeers() {

        List<?> beerDtos = restTemplate.getForObject("http://localhost:" + port + "/api/v1/beer",
                List.class);
        assertThat(beerDtos).hasSize(3);

        log.info(beerDtos.toString());
    }

    @Disabled(" Doesn't work properly!!! To check")
    @Rollback
    @Transactional
    @Test
    void testEmptyList() {

        beerRepo.deleteAll();

        List<?> beerDtos = restTemplate.getForObject("http://localhost:" + port + "/api/v1/beer",
                List.class);
        assertThat(beerDtos).isEmpty();

        log.info(beerDtos.toString());
    }
}