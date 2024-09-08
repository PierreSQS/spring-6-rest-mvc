package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.mappers.BeerOrderMapper;
import guru.springframework.spring6restmvc.model.BeerOrderDTO;
import guru.springframework.spring6restmvc.repositories.BeerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BeerOrderController {
    public static final String BEER_ORDER_PATH = "/api/v1/beerorder";
    public static final String BEER_ORDER_PATH_ID = BEER_ORDER_PATH + "/{beerOrderID}";

    private final BeerOrderRepository beerOrderRepo;
    private final BeerOrderMapper beerOrderMapper;

    @GetMapping(BEER_ORDER_PATH)
    public List<BeerOrderDTO> listBeerOrders() {
        return null;
    }

    @GetMapping(BEER_ORDER_PATH_ID)
    public BeerOrderDTO getBeerOrderByID(@PathVariable UUID beerOrderID) {
        return null;
    }
}
