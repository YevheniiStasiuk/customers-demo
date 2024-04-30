package com.example.customersdemo.service;

import com.example.customersdemo.dto.CreateCustomerDto;
import com.example.customersdemo.dto.UpdateCustomerDto;
import com.example.customersdemo.exception.EntityAlreadyExistsException;
import com.example.customersdemo.exception.EntityNotFoundException;
import com.example.customersdemo.model.Customer;
import com.example.customersdemo.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CreateCustomerDto createCustomerDto) {
        if (customerRepository.existsByEmail(createCustomerDto.getEmail())) {
            throw new EntityAlreadyExistsException();
        }

        final Customer customer = modelMapper.map(createCustomerDto, Customer.class);
        return customerRepository.save(customer);
    }

    public List<Customer> readAllCustomers() {
        return customerRepository.findByIsActiveTrue();
    }

    public Customer readCustomerById(long id) {
        return customerRepository.findByIdAndIsActiveTrue(id).orElseThrow(EntityNotFoundException::new);
    }

    public Customer fullUpdateCustomer(long id, UpdateCustomerDto updateCustomerDto) {
        final Customer customer = readCustomerById(id);
        modelMapper.map(updateCustomerDto, customer);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(long id) {
        final Customer customer = readCustomerById(id);
        customer.setIsActive(false);
        customerRepository.save(customer);
    }
}
