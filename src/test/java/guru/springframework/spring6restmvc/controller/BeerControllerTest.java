package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import guru.springframework.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static guru.springframework.spring6restmvc.controller.BeerController.BEER_PATH;
import static guru.springframework.spring6restmvc.controller.BeerController.BEER_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor ArgumentCaptor<Beer> beerArgumentCaptor;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp() {
        beerServiceImpl  = new BeerServiceImpl();
    }

    @Test
    void testPatchBeer() throws Exception {
        // Given
        Beer beerToPatch = beerServiceImpl.listBeers().getLast();
        Map<String, String> beerMap = new HashMap<>();
        beerMap.put("beerName", "New  Name");


        mockMvc.perform(patch(BEER_PATH_ID,beerToPatch.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(beerServMock, times(1))
                .patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(beerMap).containsEntry("beerName", beerMap.get("beerName"));

        assertThat(beerToPatch.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }

    @Test
    void deleteBeer() throws Exception{
        // Given
        Beer beerToDelete = beerServiceImpl.listBeers().getFirst();

        // When, Then
        mockMvc.perform(delete(BEER_PATH_ID, beerToDelete.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(beerServMock, times(1))
                .deleteById(uuidArgumentCaptor.capture());

        assertThat(beerToDelete.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void updateBeer() throws Exception {
        // Given
        Beer updatedBeer = beerServiceImpl.listBeers().getFirst();

        // When, Then
        mockMvc.perform(put(BEER_PATH_ID, updatedBeer.getId())
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
        mockMvc.perform(post(BEER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerToCreate)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        equalTo(BEER_PATH+"/"+createdBeer.getId().toString())))
                .andDo(print())
                .andReturn();

    }

    @Test
    void testListBeers() throws Exception {
        given(beerServMock.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BEER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getBeerById() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().getFirst();

        given(beerServMock.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get(BEER_PATH_ID,testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }
}