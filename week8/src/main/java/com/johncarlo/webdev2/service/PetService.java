package main.java.com.johncarlo.webdev2.service;

import com.yourpackage.model.Pet;
import com.yourpackage.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet registerPet(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found"));
    }

    public Pet updatePet(Long id, Pet updatedPet) {
        Pet existing = getPetById(id);
        existing.setName(updatedPet.getName());
        existing.setSpecies(updatedPet.getSpecies());
        existing.setBreed(updatedPet.getBreed());
        existing.setMicrochipNumber(updatedPet.getMicrochipNumber());
        return petRepository.save(existing);
    }

    public void removePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        }
        petRepository.deleteById(id);
    }

    @Transactional
    public void batchRegister(List<Pet> pets) {
        for (Pet p : pets) {
            petRepository.save(p);
        }
    }

    public List<Pet> findBySpeciesIgnoreCase(String species) {
        return petRepository.findBySpeciesIgnoreCase(species);
    }

    public List<Pet> findByNameContainingIgnoreCase(String keyword) {
        return petRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<String> findAllSpecies() {
        return petRepository.findAllSpecies();
    }
}
