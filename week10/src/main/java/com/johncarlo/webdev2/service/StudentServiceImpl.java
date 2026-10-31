package com.johncarlo.webdev2.service.impl;

import com.johncarlo.webdev2.dto.CreateStudentRequest;
import com.johncarlo.webdev2.dto.StudentResponseDTO;
import com.johncarlo.webdev2.dto.UpdateStudentRequest;
import com.johncarlo.webdev2.exception.ResourceNotFoundException;
import com.johncarlo.webdev2.model.Student;
import com.johncarlo.webdev2.repository.StudentRepository;
import com.johncarlo.webdev2.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repo;

    private StudentResponseDTO toDTO(Student entity) {
        return StudentResponseDTO.builder()
                .id(entity.getId())
                .studentName(entity.getStudentName())
                .email(entity.getEmail())
                .build();
    }

    @Override
    public List<StudentResponseDTO> findAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public StudentResponseDTO findById(Long id) {
        return repo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found id: " + id));
    }

    @Override
    public StudentResponseDTO create(CreateStudentRequest request) {
        if(repo.existsByEmail(request.getEmail())){
            throw new DuplicateEmailException("Email already exists");
        }
        Student entity = Student.builder()
                .studentName(request.getStudentName())
                .email(request.getEmail())
                .build();
        entity = repo.save(entity);
        return toDTO(entity);
    }

    @Override
    public StudentResponseDTO fullUpdate(Long id, UpdateStudentRequest request) {
        Student entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        entity.setStudentName(request.getStudentName());
        entity.setEmail(request.getEmail());
        entity = repo.save(entity);
        return toDTO(entity);
    }

    @Override
    public StudentResponseDTO partialUpdate(Long id, UpdateStudentRequest request) {
        Student entity = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        if(request.getStudentName() != null) entity.setStudentName(request.getStudentName());
        if(request.getEmail() != null) entity.setEmail(request.getEmail());
        entity = repo.save(entity);
        return toDTO(entity);
    }

    @Override
    public void delete(Long id) {
        if(!repo.existsById(id)) throw new ResourceNotFoundException("Student not found");
        repo.deleteById(id);
    }
}
