package com.johncarlo.webdev2.service;

import com.johncarlo.webdev2.dto.StudentResponse;
import com.johncarlo.webdev2.model.Student;
import com.johncarlo.webdev2.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceNotFoundException;


@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    public Page<StudentResponse> getAll(
            int page,
            int size,
            String sort,
            String direction,
            String program) {
        // Cap max size at 100
        int cappedSize = Math.min(size, 100);
        Sort.Direction sortDir = direction.equalsIgnoreCase("desc") 
                ? Sort.Direction.DESC 
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, cappedSize, Sort.by(sortDir, sort));
        Page<Student> students;
        if (program != null && !program.isBlank()) {
            students = studentRepository.findByProgramContainingIgnoreCase(program, pageable);
        } else {
            students = studentRepository.findAll(pageable);
        }
        return students.map(this::toResponse);
    }

    private StudentResponse toResponse(Student student) {
        StudentResponse resp = new StudentResponse();
        resp.setId(student.getId());
        resp.setFirstName(student.getFirstName());
        resp.setLastName(student.getLastName());
        resp.setEmail(student.getEmail());
        resp.setProgram(student.getProgram());
        return resp;
    }
}

