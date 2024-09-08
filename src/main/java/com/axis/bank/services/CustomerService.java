package com.axis.bank.services;

import com.axis.bank.entities.Customer;
import com.axis.bank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerByAccountId(String accountId) {
        return customerRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for account ID: " + accountId));
    }
}