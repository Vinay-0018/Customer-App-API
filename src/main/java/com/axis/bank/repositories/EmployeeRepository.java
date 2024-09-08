package com.axis.bank.repositories;

import com.axis.bank.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmployeeId(String employeeId);
}
