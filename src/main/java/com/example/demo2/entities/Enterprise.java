package com.example.demo2.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "enterprise")
public class Enterprise extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private boolean status;

    @Column
    @NotNull
    private String address;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String phone;

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL)
    private List<Department> departments;

    public Enterprise(boolean status, String address, String name, String phone) {
        this.status = status;
        this.address = address;
        this.name = name;
        this.phone = phone;
    }

    public Enterprise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
        for(Department dpt: departments) {
            dpt.setEnterprise(this);
        }
    }
}
