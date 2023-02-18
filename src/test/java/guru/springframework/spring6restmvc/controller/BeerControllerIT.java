package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * this Test is an Integration Test just to check
 * the interaction between controller and service
 * in isolation without the Web-Layer
 * <p>
 * Created by Pierrot on 18.02.2023.
 */
@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepo;

    @Test
    void testListBeer() {
        List<BeerDTO> allBeers = beerController.listBeers();

        assertThat(allBeers).hasSize(3);
    }
    @Rollback
    @Transactional
    @Test
    void testEmptyListBeer() {
        beerRepo.deleteAll();
        List<BeerDTO> allBeers = beerController.listBeers();

        assertThat(allBeers).isEmpty();
    }
}