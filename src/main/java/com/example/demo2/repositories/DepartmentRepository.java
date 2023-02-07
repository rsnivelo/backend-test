package com.example.demo2.repositories;

import com.example.demo2.entities.Department;
import com.example.demo2.entities.Enterprise;
import com.example.demo2.response.DepartmentDTO;
import com.example.demo2.response.EnterpriseDepartmentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByEnterpriseId(Long enterpriseId);

    @Query(value = "SELECT new com.example.demo2.response.EnterpriseDepartmentDTO( d.id, d.name, e.name) from Department d INNER JOIN d.enterprise e order by e.name")
    List<EnterpriseDepartmentDTO> findAllWithEnterprise();
}
