package com.johncarlo.webdev2.service;

import com.example.lab9.model.Student;
import com.example.lab9.repository.StudentRepository;
import com.example.lab9.exception.StudentNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    public StudentService(StudentRepository r){repo=r;}

    public List<Student> getAll(){return repo.findAll();}
    public Student getById(Long id){return repo.findById(id).orElseThrow(() -> new StudentNotFoundException(id));}
    public Student save(Student s){return repo.save(s);}
    public Student update(Long id, Student s){
        Student found = getById(id);
        found.setName(s.getName()); found.setCourse(s.getCourse());
        return repo.save(found);
    }
    public void delete(Long id){repo.deleteById(id);}
}

