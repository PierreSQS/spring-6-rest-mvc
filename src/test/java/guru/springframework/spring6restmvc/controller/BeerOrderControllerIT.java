package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.BeerOrder;
import guru.springframework.spring6restmvc.repositories.BeerOrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static guru.springframework.spring6restmvc.controller.BeerOrderController.BEER_ORDER_PATH;
import static guru.springframework.spring6restmvc.controller.BeerOrderController.BEER_ORDER_PATH_ID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BeerOrderControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BeerOrderRepository beerOrderRepo;

    @Test
    void listBeerOrders() throws Exception {
        mockMvc.perform(get(BEER_ORDER_PATH)
                        .with(BeerControllerTest.jwtRequestPostProcessor))
                .andExpect(status().isOk())
                // 6 is the minimum amount of BeerOrders coming from the BootStrapLoader
                .andExpect(jsonPath("$.content.size()", is(greaterThanOrEqualTo(6))))
                .andDo(print());
    }

    @Test
    void getBeerOrderByID() throws Exception {
        // find an existing order e.g. the first one in the BeerOrderRepository
        BeerOrder firstFoundBeer = beerOrderRepo.findAll().getFirst();

        mockMvc.perform(get(BEER_ORDER_PATH_ID,firstFoundBeer.getId().toString())
                        .with(BeerControllerTest.jwtRequestPostProcessor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",equalTo(firstFoundBeer.getId().toString())))
                .andDo(print());
    }
}