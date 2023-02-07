package com.example.demo2.response;

public class EnterpriseDepartmentDTO {

    private Long id;

    private String name;

    private String enterprise;

    public EnterpriseDepartmentDTO() {
    }

    public EnterpriseDepartmentDTO(Long id, String name, String enterprise) {
        this.id = id;
        this.name = name;
        this.enterprise = enterprise;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
}
