package com.johncarlo.webdev2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthorCreateDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @Size(max = 50, message = "Nationality cannot exceed 50 characters")
    private String nationality;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
}
