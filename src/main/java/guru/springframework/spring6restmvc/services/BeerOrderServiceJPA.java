package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.controller.NotFoundException;
import guru.springframework.spring6restmvc.entities.BeerOrder;
import guru.springframework.spring6restmvc.entities.BeerOrderLine;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.BeerOrderMapper;
import guru.springframework.spring6restmvc.model.BeerOrderCreateDTO;
import guru.springframework.spring6restmvc.model.BeerOrderDTO;
import guru.springframework.spring6restmvc.model.BeerOrderLineDTO;
import guru.springframework.spring6restmvc.repositories.BeerOrderRepository;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Pierrot on 09-09-2024.
 */
@Service
@RequiredArgsConstructor
public class BeerOrderServiceJPA implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepo;
    private final BeerOrderMapper beerOrderMapper;
    private final CustomerRepository customerRepo;
    private final BeerRepository beerRepo;

    @Override
    public BeerOrder saveBeerOrder(BeerOrderCreateDTO toCreateBeerOrderDTO) {
        // find the Customer of the BeerOrder or throw a NFE
        Customer customer = customerRepo.findById(toCreateBeerOrderDTO.getCustomerID())
                .orElseThrow(NotFoundException::new);

        // prepare Set of BeerOrderLines
        Set<BeerOrderLine> beerOrderLines = new HashSet<>();

        // set Beer and Quantity in BeerOrderLine to BeerOrder to create
        toCreateBeerOrderDTO.getBeerOrderLineCreateDTOS().forEach(beerOrderLineCreateDTO -> {
        });

        return null;
    }

    @Override
    public Optional<BeerOrderDTO> getById(UUID beerOrderID) {
        BeerOrder beerOrder = beerOrderRepo.findById(beerOrderID).orElse(null);
        return Optional.ofNullable(beerOrderMapper.beerOrderToBeerOrderDto(beerOrder));
    }

    @Override
    public Page<BeerOrderDTO> listOrders(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = 0;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }

        return beerOrderRepo.findAll(PageRequest.of(pageNumber, pageSize))
                .map(beerOrderMapper::beerOrderToBeerOrderDto);
    }
}
