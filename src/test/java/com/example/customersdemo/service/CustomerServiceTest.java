package com.example.customersdemo.service;

import com.example.customersdemo.dto.CreateCustomerDto;
import com.example.customersdemo.dto.UpdateCustomerDto;
import com.example.customersdemo.exception.EntityAlreadyExistsException;
import com.example.customersdemo.exception.EntityNotFoundException;
import com.example.customersdemo.model.Customer;
import com.example.customersdemo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    private static Customer defaultCustomer;
    private static CreateCustomerDto defaultCreateCustomerDto;
    private static UpdateCustomerDto defaultUpdateCustomerDto;

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
    }

    @Test
    public void testCreateCustomerSuccess() {
        when(customerRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(defaultCustomer);

        Customer customer = customerService.createCustomer(defaultCreateCustomerDto);

        assertNotNull(customer);
        assertEquals(defaultCustomer, customer);
    }

    @Test
    public void testCreateCustomerEntityAlreadyExists() {
        when(customerRepository.existsByEmail(any(String.class))).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> customerService.createCustomer(defaultCreateCustomerDto));
    }

    @Test
    public void testReadAllCustomersSuccess() {
        when(customerRepository.findByIsActiveTrue()).thenReturn(List.of(defaultCustomer));

        List<Customer> customerList = customerService.readAllCustomers();

        assertNotNull(customerList);
        assertEquals(defaultCustomer, customerList.get(0));
    }

    @Test
    public void testReadCustomerByIdSuccess() {
        when(customerRepository.findByIdAndIsActiveTrue(any(Long.class))).thenReturn(Optional.of(defaultCustomer));

        Customer customer = customerService.readCustomerById(1L);

        assertNotNull(customer);
        assertEquals(defaultCustomer, customer);
    }

    @Test
    public void testReadCustomerByIdEntityNotFound() {
        when(customerRepository.findByIdAndIsActiveTrue(any(Long.class))).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> customerService.readCustomerById(1L));
    }

    @Test
    public void testFullUpdateCustomerSuccess() {
        when(customerRepository.findByIdAndIsActiveTrue(any(Long.class))).thenReturn(Optional.of(defaultCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(defaultCustomer);

        Customer customer = customerService.fullUpdateCustomer(1L, defaultUpdateCustomerDto);

        assertNotNull(customer);
        assertEquals(defaultCustomer, customer);
    }

    @Test
    public void testFullUpdateCustomerEntityNotFound() {
        when(customerRepository.findByIdAndIsActiveTrue(any(Long.class))).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> customerService.fullUpdateCustomer(1L, defaultUpdateCustomerDto));
    }
}
