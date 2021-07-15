package com.ith.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ith.msscbrewery.web.model.BeerDto;
import com.ith.msscbrewery.web.services.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDto validBeer;

    @BeforeEach
    void setUp() {
        validBeer=BeerDto.builder().id(UUID.randomUUID())
                .beerName("Beer1")
                .beerStyle("PALE_ALE")
                .upc(123456L)
                .build();
    }

    @Test
    void getBeer() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(validBeer);

        mockMvc.perform(get("/api/v1/beer/"+validBeer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(validBeer.getId().toString()))
                .andExpect(jsonPath("$.beerName").value("Beer1"));
    }

    @Test
    void handlePost() throws Exception {
        BeerDto beerDto=validBeer;
        beerDto.setId(null);
        BeerDto saveDto=BeerDto.builder().id(UUID.randomUUID()).beerName("New Beer").build();
        String beerDtoJson=objectMapper.writeValueAsString(beerDto);

        given(beerService.saveNewBeer(any())).willReturn(saveDto);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());

        then(beerService).should().saveNewBeer(any());
    }

    @Test
    void handleUpdate() throws Exception{
        BeerDto beerDto=validBeer;
        beerDto.setId(null);
        String beerDtoJson=objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/"+UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());

        then(beerService).should().updateBeer(any(),any());
    }


    @Test
    void deleteBeer() throws Exception{
        BeerDto beerDto=validBeer;

        mockMvc.perform(delete("/api/v1/beer/"+beerDto.getId().toString()))
                .andExpect(status().isNoContent());

        then(beerService).should().deleteBeerById(any());
    }
}