package com.johncarlo.webdev2.controller;

import com.example.lab9.model.Student;
import com.example.lab9.service.StudentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService s){service=s;}

    @GetMapping
    public List<Student> getAll(){return service.getAll();}
    @GetMapping("/{id}")
    public Student getOne(@PathVariable Long id){return service.getById(id);}
    @PostMapping
    public Student create(@RequestBody Student s){return service.save(s);}
    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student s){return service.update(id,s);}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){service.delete(id);}
}

