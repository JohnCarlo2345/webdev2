package com.johncarlo.webdev2.controller;

import com.johncarlo.webdev2.dto.CreateStudentRequest;
import com.johncarlo.webdev2.dto.StudentResponseDTO;
import com.johncarlo.webdev2.dto.UpdateStudentRequest;
import com.johncarlo.webdev2.exception.ResourceNotFoundException;
import com.johncarlo.webdev2.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> listAll(){
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(studentService.findById(id));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@RequestBody CreateStudentRequest req){
        try{
            StudentResponseDTO saved = studentService.create(req);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();
            return ResponseEntity.created(location).body(saved);
        }catch(DuplicateEmailException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> fullUpdate(@PathVariable Long id, @RequestBody UpdateStudentRequest req){
        try{
            return ResponseEntity.ok(studentService.fullUpdate(id, req));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> partialUpdate(@PathVariable Long id, @RequestBody UpdateStudentRequest req){
        try{
            return ResponseEntity.ok(studentService.partialUpdate(id, req));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            studentService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
