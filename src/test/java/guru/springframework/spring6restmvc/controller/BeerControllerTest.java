package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import guru.springframework.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerServMock;


    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() {
        beerServiceImpl  = new BeerServiceImpl();
    }

    @Test
    void testPatchBeer() throws Exception {
        // Given
        Beer beerToPatch = beerServiceImpl.listBeers().getLast();
        beerToPatch.setBeerName("Beer To Patch");

        mockMvc.perform(patch("/api/v1/beer/{beerID}",beerToPatch.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerToPatch)))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(beerServMock, times(1))
                .patchBeerById(beerToPatch.getId(), beerToPatch);

    }

    @Test
    void deleteBeer() throws Exception{
        // Given
        Beer beerToDelete = beerServiceImpl.listBeers().getFirst();

        // When, Then
        mockMvc.perform(delete("/api/v1/beer/{beerID}", beerToDelete.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(beerServMock, times(1))
                .deleteById(beerToDelete.getId());
    }

    @Test
    void updateBeer() throws Exception {
        // Given
        Beer updatedBeer = beerServiceImpl.listBeers().getFirst();

        // When, Then
        mockMvc.perform(put("/api/v1/beer/{beerID}", updatedBeer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBeer)))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(beerServMock, times(1))
                .updateBeerById(updatedBeer.getId(),updatedBeer);

    }

    @Test
    void testCreateNewBeer() throws Exception {
        // Given
        Beer beerToCreate = beerServiceImpl.listBeers().getFirst();
        beerToCreate.setVersion(null);
        beerToCreate.setId(null);

        Beer createdBeer = beerServiceImpl.listBeers().get(1);

        given(beerServMock.saveNewBeer(any(Beer.class)))
                .willReturn(createdBeer);

        // When, Then
        mockMvc.perform(post("/api/v1/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerToCreate)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        equalTo("/api/v1/beer/"+createdBeer.getId().toString()))
                )
                .andDo(print())
                .andReturn();

    }

    @Test
    void testListBeers() throws Exception {
        given(beerServMock.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getBeerById() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().getFirst();

        given(beerServMock.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }
}