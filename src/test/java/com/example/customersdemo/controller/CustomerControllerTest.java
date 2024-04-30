package com.example.customersdemo.controller;

import com.example.customersdemo.dto.CreateCustomerDto;
import com.example.customersdemo.dto.UpdateCustomerDto;
import com.example.customersdemo.model.Customer;
import com.example.customersdemo.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private static final String URL = "/api/customers";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final ModelMapper modelMapper = new ModelMapper();

    private static Customer defaultCustomer;
    private static CreateCustomerDto defaultCreateCustomerDto;
    private static UpdateCustomerDto defaultUpdateCustomerDto;
    private static Customer badCustomer;
    private static CreateCustomerDto badCreateCustomerDto;
    private static UpdateCustomerDto badUpdateCustomerDto;

    @BeforeAll
    public static void setUp() {
        defaultCustomer = new Customer(1L,
                new Date().getTime(),
                new Date().getTime(),
                "Full name",
                "test@mail.com",
                "+380631837586",
                true);

        defaultCreateCustomerDto = modelMapper.map(defaultCustomer, CreateCustomerDto.class);
        defaultUpdateCustomerDto = modelMapper.map(defaultCustomer, UpdateCustomerDto.class);

        badCustomer = new Customer(1L,
                new Date().getTime(),
                new Date().getTime(),
                "Full name",
                "bad email",
                "000",
                true);

        badCreateCustomerDto = modelMapper.map(badCustomer, CreateCustomerDto.class);
        badUpdateCustomerDto = modelMapper.map(badCustomer, UpdateCustomerDto.class);
    }

    @Test
    public void createCustomerSuccess() throws Exception {
        when(customerService.createCustomer(any(CreateCustomerDto.class))).thenReturn(defaultCustomer);

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrapToJson(defaultCreateCustomerDto)))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreateCustomerBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrapToJson(badCreateCustomerDto)))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testReadAllCustomersSuccess() throws Exception {
        when(customerService.readAllCustomers()).thenReturn(List.of(defaultCustomer));

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testReadCustomerByIdSuccess() throws Exception {
        when(customerService.readCustomerById(any(Long.class))).thenReturn(defaultCustomer);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/1"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFullUpdateCustomerSuccess() throws Exception {
        when(customerService.fullUpdateCustomer(any(Long.class), any(UpdateCustomerDto.class))).thenReturn(defaultCustomer);

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrapToJson(defaultUpdateCustomerDto)))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFullUpdateCustomerBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrapToJson(badUpdateCustomerDto)))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDeleteCustomerSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/1"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String wrapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
