package com.axis.bank.controllers;

import com.axis.bank.entities.Customer;
import com.axis.bank.entities.Employee;
import com.axis.bank.entities.Role;
import com.axis.bank.entities.User;
import com.axis.bank.repositories.CustomerRepository;
import com.axis.bank.repositories.EmployeeRepository;
import com.axis.bank.repositories.RoleRepository;
import com.axis.bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Set;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/register")
public class RegisterController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/admin")
    public String adminRegister(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role roles = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Role Not Found!!"));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roles);
        user.setRoles(roleSet);
        userRepository.save(user);
        return "Admin saved Successfully";
    }
    @PostMapping("/customer")
    public String customerRegister(@RequestBody Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
        Role roles = roleRepository.findByName("CUSTOMER").orElseThrow(() -> new RuntimeException("Role Not Found!!"));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roles);
        User user = new User();
        user.setUsername(customer.getAccountId());
        user.setPassword(customer.getPassword());
        user.setRoles(roleSet);
        userRepository.save(user);
        return "Customer saved successfully and User also Created";
    }

    @PostMapping("/employee")
    public String employeeRegister(@RequestBody Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        Role roles = roleRepository.findByName("EMPLOYEE").orElseThrow(() -> new RuntimeException("User Not Found!!"));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roles);
        User user = new User();
        user.setUsername(employee.getEmployeeId());
        user.setPassword(employee.getPassword());
        user.setRoles(roleSet);
        userRepository.save(user);
        employeeRepository.save(employee);
        return "Employee saved successfully and User also Created";
    }
    @GetMapping("/{username}")
    public String getUser(@PathVariable String username) {
            Customer customer = customerRepository.findByAccountId(username).orElse(null);
            if (customer != null) {
                return customer.getEmail();
            }

            Employee employee = employeeRepository.findByEmployeeId(username).orElse(null);
            if (employee != null) {
                return employee.getEmail();
            }
            return "User not found";
    }
    @PutMapping("/forgot")
    public String updateUser(@RequestBody User user) {
        User user1 = userRepository.findByUsername(user.getUsername()).orElse(null);
        if(user1 != null){
            user.setId(user1.getId());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Customer customer = customerRepository.findByAccountId(user.getUsername()).orElse(null);
            if (customer != null) {
                customer.setPassword(user.getPassword());
                Role role = roleRepository.findByName("CUSTOMER").orElseThrow(()->new RuntimeException("Role Not Found"));
                user.setRoles(new HashSet<Role>(){{add(role);}});
                userRepository.save(user);
                customerRepository.save(customer);
                return "Password Updated";
            }

            Employee employee = employeeRepository.findByEmployeeId(user.getUsername()).orElse(null);
            if (employee != null) {
                employee.setPassword(user.getPassword());
                Role role = roleRepository.findByName("EMPLOYEE").orElseThrow(()->new RuntimeException("Role Not Found"));
                user.setRoles(new HashSet<Role>(){{add(role);}});
                userRepository.save(user);
                employeeRepository.save(employee);
                return "Password Updated";
            }
        }
        return "Not updated or User not found";
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public String duplicateUserHandler(){ return "User already exists !!"; }
}
