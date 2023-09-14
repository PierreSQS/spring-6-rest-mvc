package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepo;

    @Test
    void testBeerIdNotFound() {
        UUID beerId = UUID.randomUUID();

        // AssertJ Exception Assertion
        assertThatThrownBy(() -> beerController.getBeerById(beerId))
                .isInstanceOf(NotFoundException.class);

        // Junit Exception Assertion
        assertThrows(NotFoundException.class, () ->
                beerController.getBeerById(beerId));
    }

    @Test
    void testGetById() {
        Beer beer = beerRepo.findAll().get(0);
        BeerDTO beerById = beerController.getBeerById(beer.getId());
        assertThat(beerById.getBeerName()).isEqualTo("Galaxy Cat");
    }

    @Test
    void testListBeers() {
        List<BeerDTO> beerDTOList = beerController.listBeers();
        assertThat(beerDTOList).hasSize(3);

    }

    @Transactional
    @Rollback
    @Test
    void testEmptyList() {
        beerRepo.deleteAll();
        List<BeerDTO> beerDTOList = beerController.listBeers();
        assertThat(beerDTOList).isEmpty();
    }
}
