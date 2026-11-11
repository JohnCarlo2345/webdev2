package com.johncarlo.webdev2.repository;

import com.johncarlo.webdev2.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByProgramContainingIgnoreCase(String program, Pageable pageable);
}

