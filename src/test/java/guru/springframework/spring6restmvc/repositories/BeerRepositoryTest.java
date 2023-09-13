package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepo;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepo.save(Beer.builder().beerName("New Beer").build());
        assertThat(savedBeer.getBeerName()).isEqualTo("New Beer");

        UUID uuid = savedBeer.getId();
        log.info("the Beer UUID: {}",uuid);
        assertThat(uuid).isNotNull();

    }
}