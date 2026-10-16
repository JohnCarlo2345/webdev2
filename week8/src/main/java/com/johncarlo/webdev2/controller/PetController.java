package main.java.com.johncarlo.webdev2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pet registerPet(@RequestBody Pet pet) {
        return petService.registerPet(pet);
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @PutMapping("/{id}")
    public Pet updatePet(@PathVariable Long id, @RequestBody Pet updatedPet) {
        return petService.updatePet(id, updatedPet);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePet(@PathVariable Long id) {
        petService.removePet(id);
    }

    // Task 3 endpoints
    @GetMapping("/species")
    public List<Pet> getBySpecies(@RequestParam String name) {
        return petService.findBySpeciesIgnoreCase(name);
    }

    @GetMapping("/search")
    public List<Pet> searchByName(@RequestParam String keyword) {
        return petService.findByNameContainingIgnoreCase(keyword);
    }

    @GetMapping("/species-list")
    public List<String> getAllSpecies() {
        return petService.findAllSpecies();
    }

    // Task 4 endpoint
    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public void batchRegister(@RequestBody List<Pet> pets) {
        petService.batchRegister(pets);
    }
}
