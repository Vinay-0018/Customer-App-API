package com.axis.bank.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.axis.bank.entities.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByAccountId(String accountId);
    Optional<Customer> findByEmail(String email);
}
