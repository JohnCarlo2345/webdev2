package com.johncarlo.webdev2.dto;

public StudentResponse getById(Long id) {
    Student student = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
}
