package com.example.demo2.repositories;

import com.example.demo2.entities.DepartmentEmployee;
import com.example.demo2.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployee, Long> {
    List<DepartmentEmployee> findByEmployeeId(Long employeeId);

}
