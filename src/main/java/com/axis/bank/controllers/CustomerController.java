package com.axis.bank.controllers;

import com.axis.bank.entities.Customer;
import com.axis.bank.repositories.CustomerRepository;
import com.axis.bank.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/{accountId}")
    public ResponseEntity<Customer> getCustomerByAccountId(@PathVariable String accountId) {
        Customer customer = customerService.getCustomerByAccountId(accountId);
        return ResponseEntity.ok(customer);
    }
    @PutMapping
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        Customer customer1 = customerService.getCustomerByAccountId(customer.getAccountId());
        customer.setId(customer1.getId());
        return ResponseEntity.ok(customerRepository.save(customer));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFound(){return "Customer Does Not Exits.."; }
}
