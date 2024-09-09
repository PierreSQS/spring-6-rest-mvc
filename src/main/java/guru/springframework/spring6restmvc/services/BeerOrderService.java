package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerOrderDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Pierrot on 09-09-2024.
 */
public interface BeerOrderService {

    Optional<BeerOrderDTO> getById(UUID beerOrderID);

    Page<BeerOrderDTO> listOrders(Integer pageNumber, Integer pageSize);
}
