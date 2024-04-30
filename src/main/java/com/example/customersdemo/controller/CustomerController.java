package com.example.customersdemo.controller;

import com.example.customersdemo.dto.CreateCustomerDto;
import com.example.customersdemo.dto.FullCustomerDto;
import com.example.customersdemo.dto.ResponseMessageDto;
import com.example.customersdemo.dto.UpdateCustomerDto;
import com.example.customersdemo.model.Customer;
import com.example.customersdemo.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<FullCustomerDto> createCustomer(@RequestBody @Validated CreateCustomerDto createCustomerDto) {
        final Customer customer = customerService.createCustomer(createCustomerDto);
        final FullCustomerDto fullCustomerDto = modelMapper.map(customer, FullCustomerDto.class);
        return new ResponseEntity<>(fullCustomerDto, CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FullCustomerDto>> readAllCustomers() {
        final List<FullCustomerDto> fullCustomerDtoList = customerService.readAllCustomers().stream()
                .map((customer -> modelMapper.map(customer, FullCustomerDto.class)))
                .toList();
        return new ResponseEntity<>(fullCustomerDtoList, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullCustomerDto> readCustomerById(@PathVariable long id) {
        final Customer customer = customerService.readCustomerById(id);
        final FullCustomerDto fullCustomerDto = modelMapper.map(customer, FullCustomerDto.class);
        return new ResponseEntity<>(fullCustomerDto, OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FullCustomerDto> fullUpdateCustomer(@PathVariable long id,
                                                              @RequestBody @Validated UpdateCustomerDto updateCustomerDto) {
        final Customer customer = customerService.fullUpdateCustomer(id, updateCustomerDto);
        final FullCustomerDto fullCustomerDto = modelMapper.map(customer, FullCustomerDto.class);
        return new ResponseEntity<>(fullCustomerDto, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDto> deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(new ResponseMessageDto(OK.value(), "Customer has been deleted"), OK);
    }
}
