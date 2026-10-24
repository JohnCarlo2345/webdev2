package com.example.lab9.model;

import jakarta.persistence.*;

@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String course;

    public Student(){}
    public Student(Long id, String name, String course){
        this.id=id; this.name=name; this.course=course;
    }
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getCourse(){return course;}
    public void setCourse(String course){this.course=course;}
}

