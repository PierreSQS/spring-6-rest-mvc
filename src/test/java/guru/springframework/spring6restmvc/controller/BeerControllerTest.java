package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    Beer beer;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerServMock;

    @BeforeEach
    void setUp() {
        beer = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Test Bier").upc("12345")
                .createdDate(LocalDateTime.now())
                .build();
    }
    @Test
    void getBeerById() throws Exception {

        given(beerServMock.getBeerById(any(UUID.class))).willReturn(beer);
        mockMvc.perform(get("/api/v1/beer/" + beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beerName").value("Test Bier"))
                .andDo(print());
    }
}