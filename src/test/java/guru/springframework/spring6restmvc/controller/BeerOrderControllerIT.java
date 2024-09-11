package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.BeerOrder;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.BeerOrderCreateDTO;
import guru.springframework.spring6restmvc.model.BeerOrderLineCreateDTO;
import guru.springframework.spring6restmvc.repositories.BeerOrderRepository;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static guru.springframework.spring6restmvc.controller.BeerOrderController.BEER_ORDER_PATH;
import static guru.springframework.spring6restmvc.controller.BeerOrderController.BEER_ORDER_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    BeerRepository beerRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createBeerOrder() throws Exception {
        // find an existing customer and set an order and orderlines on it
        Customer firstFoundCustomer = customerRepo.findAll().getFirst();

        // find an existing beer and set it as reference in the beer orderline
        Beer firstFoundBeer = beerRepo.findAll().getFirst();

        BeerOrderCreateDTO beerOrderCreateDTO = BeerOrderCreateDTO.builder()
                .customerID(firstFoundCustomer.getId())
                .customerRef(firstFoundCustomer.getName())
                .beerOrderLineCreateDTOS(Set.of(
                        BeerOrderLineCreateDTO.builder()
                                .beerID(firstFoundBeer.getId())
                                .orderQuantity(1)
                                .build()
                )).build();

        MvcResult mvcResult = mockMvc.perform(post(BEER_ORDER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerOrderCreateDTO))
                        .with(BeerControllerTest.jwtRequestPostProcessor))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String location = mvcResult.getResponse().getHeader("Location");
        String redirectedUrl = mvcResult.getResponse().getRedirectedUrl();

        assertThat(location).isEqualTo(redirectedUrl);

    }

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