package com.webdev2.service;

import com.webdev2.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Roster {
    private List<Student> students;

    public Roster() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public List<String> getAdultStudentNames() {
        return students.stream()
                .filter(s -> s.getAge() >= 18)
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    public long countAdultStudents() {
        return students.stream()
                .filter(s -> s.getAge() >= 18)
                .count();
    }
}

