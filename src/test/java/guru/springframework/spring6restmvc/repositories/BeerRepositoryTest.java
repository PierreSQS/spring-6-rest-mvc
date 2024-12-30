package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepo;

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepo.save(Beer.builder().beerName("Beer To Save").build());

        assertThat(savedBeer.getUuid()).isNotNull();
    }
}