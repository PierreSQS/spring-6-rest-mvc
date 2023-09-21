package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Modified by Pierrot, 20.09.2023.
 */
public interface BeerRepository extends JpaRepository<Beer, UUID> {
    List<Beer> findBeerByBeerNameIsLikeIgnoreCase(String beerName);

    List<Beer> findBeerByBeerStyle(BeerStyle beerStyle);
}
