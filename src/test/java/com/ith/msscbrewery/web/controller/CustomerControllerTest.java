package com.ith.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ith.msscbrewery.web.model.CustomerDto;
import com.ith.msscbrewery.web.services.CustomerService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CustomerDto validCustomer;

    @BeforeEach
    void setUp() {
        validCustomer=CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Thongchai")
                .build();
    }

    @Test
    void getCustomer() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willReturn(validCustomer);

        mockMvc.perform(get("/api/v1/customer/"+validCustomer.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(validCustomer.getId().toString()))
                .andExpect(jsonPath("$.name").value(validCustomer.getName()));
    }

    @Test
    void handlePost() throws Exception {
        CustomerDto customerDto=validCustomer;
        customerDto.setId(null);
        CustomerDto saveDto=CustomerDto.builder().id(UUID.randomUUID()).name("Thongchai").build();
        String customerDtoJson=objectMapper.writeValueAsString(customerDto);

        given(customerService.saveNewCustomer(any())).willReturn(saveDto);

        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoJson))
                        .andExpect(status().isCreated());

        then(customerService).should().saveNewCustomer(any());
    }

    @Test
    void handlePut() throws Exception {
        CustomerDto customerDto=validCustomer;
        String customerDtoJson=objectMapper.writeValueAsString(customerDto);

        mockMvc.perform(put("/api/v1/customer/"+customerDto.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson))
                .andExpect(status().isNoContent());

        then(customerService).should().updateCustomer(any(),any());
    }

    @Test
    void handleDelete() throws Exception {
        CustomerDto customerDto=validCustomer;

        mockMvc.perform(delete("/api/v1/customer/"+customerDto.getId().toString()))
                .andExpect(status().isNoContent());

        then(customerService).should().deleteCustomerById(any());
    }
}