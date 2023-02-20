package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * this Test is an Integration Test just to check
 * the interaction between controller and service
 * in isolation without the Web-Layer
 * <p>
 * Modified by Pierrot, 19.02.2023.
 */
@Slf4j
@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testDeleteByIDNotFound() {

        UUID beerId = UUID.randomUUID();

        EmptyResultDataAccessException nSE = assertThrows(EmptyResultDataAccessException.class,
                () -> beerController.deleteById(beerId));

        assertThat(nSE.getMessage()).contains(String.format("entity with id %s exists", beerId));

    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound() {
        Beer beerToDelete = beerRepository.findAll().get(0);

        beerController.deleteById(beerToDelete.getId());

        Optional<Beer> deletedById = beerRepository.findById(beerToDelete.getId());
        NoSuchElementException nSE = assertThrows(NoSuchElementException.class,
                deletedById::get);

        assertThat(nSE.getMessage()).isEqualTo("No value present");

    }

    @Test
    void updateBeerNotFound() {
        BeerDTO beerDTO = BeerDTO.builder().build();
        UUID beerId = UUID.randomUUID();

        NotFoundException nFE = assertThrows(NotFoundException.class,
                () -> beerController.updateById(beerId, beerDTO));

        assertThat(nFE.getMessage()).isEqualTo("Beer not found!!");

    }

    @Rollback
    @Transactional
    @Test
    void updateExistingBeer() {
        final String beerNewName = "Updated Name";
        // The Beer to update
        Beer beerToUpdate = beerRepository.findAll().get(0);

        log.info("The Beer to update: {}",beerToUpdate.getBeerName());

        // The Beer Update
        BeerDTO beerDTOChanges = BeerDTO.builder().build();
        beerDTOChanges.setId(null);
        beerDTOChanges.setVersion(null);
        beerDTOChanges.setBeerName(beerNewName);

        // Update the Beer
        ResponseEntity<Void> respEntity = beerController.updateById(beerToUpdate.getId(), beerDTOChanges);

        // Assertions on ResponseEntity
        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // Find the updated Beer in the DB to assert
        Optional<Beer> updatedBeerOpt = beerRepository.findById(beerToUpdate.getId());

        // Assertions on updated Beer
        updatedBeerOpt.ifPresent(beer -> {
            log.info("The New BeerName: {}",beer.getBeerName());
            assertThat(beer.getBeerName()).isEqualTo(beerDTOChanges.getBeerName());
        });
    }

    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity<HttpHeaders> respEntity = beerController.handlePost(beerDTO);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String locationPath = Objects.requireNonNull(respEntity.getHeaders().getLocation()).getPath();
        assertThat(locationPath).isNotNull();

        String[] pathFragments = locationPath.split("/");

        Optional<Beer> byId = beerRepository.findById(UUID.fromString(pathFragments[4]));

        byId.ifPresent(beer -> {
            log.info("Beer saved with ID {}",beer.getId());
            assertThat(beer.getBeerName()).isEqualTo("New Beer");});
    }

    @Test
    void testBeerIdNotFound() {
        UUID beerId = UUID.randomUUID();

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> beerController.getBeerById(beerId));

        assertThat(notFoundException.getMessage()).isEqualTo("Beer not found!!");
    }

    @Test
    void testGetById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerController.getBeerById(beer.getId());

        assertThat(dto.getBeerName()).isEqualTo(beer.getBeerName());
    }

    @Test
    void testListBeers() {
        List<BeerDTO> dtos = beerController.listBeers();

        assertThat(dtos).hasSize(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.listBeers();

        assertThat(dtos).isEmpty();
    }
}







