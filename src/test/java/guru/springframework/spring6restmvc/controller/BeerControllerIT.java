package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testBeerByIdNotFound() {
        UUID uuid = UUID.randomUUID();
        assertThatThrownBy(() -> beerController.getBeerById(uuid))
                .isInstanceOf(NotFoundException.class);

    }

    @Test
    void testGetBeerByID() {
        // find first Beer in the DB
        Beer beerFound = beerRepository.findAll().getLast();

        // find it from the Controller and then Assert
        BeerDTO foundBeerDTO = beerController.getBeerById(beerFound.getId());

        assertThat(foundBeerDTO.getId()).isEqualTo(beerFound.getId());

    }

    @Test
    void testListBeers() {
        List<BeerDTO> beerDTOList = beerController.listBeers();
        assertThat(beerDTOList).hasSize(3);
    }

    @Rollback
    @Transactional
    @Test
    void testListBeersEmpty() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOList = beerController.listBeers();
        assertThat(beerDTOList).isEmpty();
    }
}