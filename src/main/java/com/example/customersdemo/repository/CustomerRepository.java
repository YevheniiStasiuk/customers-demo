package com.example.customersdemo.repository;

import com.example.customersdemo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public boolean existsByEmail(String email);

    public Optional<Customer> findByIdAndIsActiveTrue(Long id);

    public List<Customer> findByIsActiveTrue();
}