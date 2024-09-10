package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.BeerOrder;
import guru.springframework.spring6restmvc.model.BeerOrderCreateDTO;
import guru.springframework.spring6restmvc.model.BeerOrderDTO;
import guru.springframework.spring6restmvc.services.BeerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BeerOrderController {
    public static final String BEER_ORDER_PATH = "/api/v1/beerorder";
    public static final String BEER_ORDER_PATH_ID = BEER_ORDER_PATH + "/{beerOrderID}";

    private final BeerOrderService beerOrderServ;

    @PostMapping(BEER_ORDER_PATH)
    public ResponseEntity<HttpHeaders> createBeerOrder(@Validated @RequestBody BeerOrderCreateDTO toCreateBeerOrderDTO) {
        BeerOrder savedBeerOrder = beerOrderServ.saveBeerOrder(toCreateBeerOrderDTO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location",BEER_ORDER_PATH + savedBeerOrder.getId());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(BEER_ORDER_PATH_ID)
    public BeerOrderDTO getBeerOrderByID(@PathVariable UUID beerOrderID) {
        return beerOrderServ.getById(beerOrderID).orElseThrow(NotFoundException::new);
    }


    @GetMapping(BEER_ORDER_PATH)
    public Page<BeerOrderDTO> listBeerOrders(@RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize) {
        return beerOrderServ.listOrders(pageNumber, pageSize);
    }

}
