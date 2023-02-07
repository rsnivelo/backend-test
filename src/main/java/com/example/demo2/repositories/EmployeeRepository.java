package com.example.demo2.repositories;

import com.example.demo2.entities.Employee;
import com.example.demo2.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
