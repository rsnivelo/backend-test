package com.example.demo2.controllers;

import com.example.demo2.entities.Enterprise;
import com.example.demo2.exceptions.EntityNotFoundException;
import com.example.demo2.repositories.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/enterprises")
@CrossOrigin(origins = "http://localhost:4200")
public class EnterpriseController {

    @Autowired
    private EnterpriseRepository repository;

    @PostMapping
    public ResponseEntity<Enterprise> save(@Valid @RequestBody Enterprise enterprise) {
        Enterprise savedEnterprise = repository.save(enterprise);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedEnterprise.getId()).toUri();
        return ResponseEntity.created(uri).body(savedEnterprise);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enterprise> update(@PathVariable Long id,@Valid @RequestBody Enterprise enterprise) {
        Optional<Enterprise> actualEnterprise = repository.findById(id);

        if (!actualEnterprise.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        enterprise.setId(actualEnterprise.get().getId());
        return ResponseEntity.ok(repository.save(enterprise));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enterprise> getById(@PathVariable Long id){
        Enterprise enterprise = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enterprise not found id = " + id));

        return ResponseEntity.ok(enterprise);
    }

    /*@GetMapping
    public ResponseEntity<Page<Enterprise>> list(Pageable pageable){
        return ResponseEntity.ok(repository.findAll(pageable));
    }*/

    @GetMapping
    public ResponseEntity<List<Enterprise>> listAll(){
        return ResponseEntity.ok(repository.findAll());
    }
}
