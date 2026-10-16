package com.johncarlo.webdev2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ✅ FIX #1: Added auto-ID generation
    private Long id;
    @Column(unique = true, nullable = false)
    private String microchipNumber;
    private String name;
    private String species;   // FIX #2: Added missing species field
    private String breed;

    public Pet() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMicrochipNumber() { return microchipNumber; }
    public void setMicrochipNumber(String microchipNumber) { this.microchipNumber = microchipNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
}
