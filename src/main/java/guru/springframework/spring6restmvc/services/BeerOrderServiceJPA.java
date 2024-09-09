package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.BeerOrder;
import guru.springframework.spring6restmvc.mappers.BeerOrderMapper;
import guru.springframework.spring6restmvc.model.BeerOrderDTO;
import guru.springframework.spring6restmvc.repositories.BeerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Pierrot on 09-09-2024.
 */
@Service
@RequiredArgsConstructor
public class BeerOrderServiceJPA implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepo;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public Optional<BeerOrderDTO> getById(UUID beerOrderID) {
        BeerOrder beerOrder = beerOrderRepo.findById(beerOrderID).orElse(null);
        return Optional.ofNullable(beerOrderMapper.beerOrderToBeerOrderDto(beerOrder));
    }

    @Override
    public Page<BeerOrderDTO> listOrders(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null) {
            pageNumber = 0;
        }

        if (pageSize == null) {
            pageSize = 25;
        }

        return beerOrderRepo.findAll(PageRequest.of(pageNumber, pageSize))
                .map(beerOrderMapper::beerOrderToBeerOrderDto);
    }
}
