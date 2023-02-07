package com.example.demo2.controllers;

import com.example.demo2.entities.Department;
import com.example.demo2.entities.DepartmentEmployee;
import com.example.demo2.entities.Employee;
import com.example.demo2.entities.Enterprise;
import com.example.demo2.repositories.DepartmentEmployeeRepository;
import com.example.demo2.repositories.DepartmentRepository;
import com.example.demo2.repositories.EmployeeRepository;
import com.example.demo2.repositories.EnterpriseRepository;
import com.example.demo2.request.EmployeeRequest;
import com.example.demo2.response.DepartmentDTO;
import com.example.demo2.response.EmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private DepartmentRepository deptRepository;

    @Autowired
    private EmployeeRepository empRepository;

    @Autowired
    private DepartmentEmployeeRepository deptEmpRepository;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> list(){
        return ResponseEntity.ok(empRepository.findAll());
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> save(@Valid @RequestBody EmployeeRequest employee){
        Optional<Department> department = deptRepository.findById(employee.getDepartmentId());

        if(!department.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        Employee newEmployee = new Employee();
        BeanUtils.copyProperties(employee, newEmployee);;
        empRepository.save(newEmployee);

        DepartmentEmployee de = new DepartmentEmployee();
        de.setEmployee(newEmployee);
        de.setDepartment(department.get());
        de.setStatus(employee.isStatus());

        deptEmpRepository.save(de);

        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/employees")
    public ResponseEntity<Department> update(@Valid @RequestBody EmployeeRequest employee){

        List<DepartmentEmployee> departmentEmployee = deptEmpRepository.findByEmployeeId(employee.getId());
        if(departmentEmployee == null || departmentEmployee.size() == 0) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Department> department = deptRepository.findById(employee.getDepartmentId());

        if(!department.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Employee> actualEmployee = empRepository.findById(employee.getId());

        if (!actualEmployee.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Employee employeeToSave = new Employee();
        BeanUtils.copyProperties(employee, employeeToSave);
        empRepository.save(employeeToSave);

        DepartmentEmployee de = departmentEmployee.get(0);
        de.setEmployee(employeeToSave);
        de.setDepartment(department.get());
        deptEmpRepository.save(de);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable Long id){
        Optional<Employee> employee = empRepository.findById(id);

        if(!employee.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee.get(), employeeDTO);
        employeeDTO.setDepartmentId(employee.get().getDepartmentEmployees().get(0).getDepartment().getId());

        return ResponseEntity.ok(employeeDTO);
    }

}
