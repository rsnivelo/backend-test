package com.example.demo2.controllers;

import com.example.demo2.entities.Department;
import com.example.demo2.entities.Enterprise;
import com.example.demo2.repositories.DepartmentRepository;
import com.example.demo2.repositories.EnterpriseRepository;
import com.example.demo2.response.DepartmentDTO;
import com.example.demo2.response.EnterpriseDepartmentDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController {

    @Autowired
    private DepartmentRepository deptRepository;

    @Autowired
    private EnterpriseRepository entRepository;

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> list(){
        return ResponseEntity.ok(deptRepository.findAll());
    }

    @PostMapping("/enterprises/{enterpriseId}/departments")
    public ResponseEntity<Department> save(@Valid @PathVariable(value = "enterpriseId") Long enterpriseId, @RequestBody Department department){
        Optional<Enterprise> enterprise = entRepository.findById(enterpriseId);

        if(!enterprise.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        department.setEnterprise(enterprise.get());
        Department savedDepartment = deptRepository.save(department);

        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> update(@Valid @RequestBody DepartmentDTO department, @PathVariable Long id){
        Optional<Enterprise> enterprise = entRepository.findById(department.getEnterpriseId());

        if(!enterprise.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Department> actualDepartment = deptRepository.findById(id);
        if(!actualDepartment.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        Department deptToSave = new Department();
        BeanUtils.copyProperties(department, deptToSave);
        deptToSave.setEnterprise(enterprise.get());
        department.setId(actualDepartment.get().getId());
        deptRepository.save(deptToSave);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<?> getById2(@PathVariable Long id){
        Optional<Department> actualDepartment = deptRepository.findById(id);

        if(!actualDepartment.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        DepartmentDTO target = new DepartmentDTO();
        BeanUtils.copyProperties(actualDepartment.get(), target);
        target.setEnterpriseId(actualDepartment.get().getEnterprise().getId());

        return ResponseEntity.ok(target);
    }

    @GetMapping("/enterprises/departments")
    public ResponseEntity<?> getEnterpriseDepartments(){
        List<EnterpriseDepartmentDTO> results = deptRepository.findAllWithEnterprise();
        return ResponseEntity.ok(results);
    }
}
