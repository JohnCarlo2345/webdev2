package com.johncarlo.webdev2.controller;

import com.johncarlo.webdev2.dto.StudentRequest;
import com.johncarlo.webdev2.dto.StudentResponse;
import com.johncarlo.webdev2.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(
            @Valid @RequestBody StudentRequest request) {
        return new ResponseEntity<>(studentService.create(request), HttpStatus.CREATED);
    }
    @GetMapping
    public Page<StudentResponse> getAllStudents(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(defaultValue = "id") String sort,
        @RequestParam(defaultValue = "asc") String direction,
        @RequestParam(required = false) String program) {
    return studentService.getAll(page, size, sort, direction, program);
}
}
