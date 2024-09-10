package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.BeerOrder;
import guru.springframework.spring6restmvc.model.BeerOrderCreateDTO;
import guru.springframework.spring6restmvc.model.BeerOrderDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

/**
 * Modified by Pierrot on 10-09-2024.
 */
public interface BeerOrderService {

    Optional<BeerOrderDTO> getById(UUID beerOrderID);

    Page<BeerOrderDTO> listOrders(Integer pageNumber, Integer pageSize);

    // we don't return a DTO!!! to check why
    BeerOrder saveBeerOrder(BeerOrderCreateDTO toCreateBeerOrderDTO);
}
