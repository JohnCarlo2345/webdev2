package com.johncarlo.webdev2.service;

import com.johncarlo.webdev2.dto.CreateStudentRequest;
import com.johncarlo.webdev2.dto.StudentResponseDTO;
import com.johncarlo.webdev2.dto.UpdateStudentRequest;
import java.util.List;

public interface StudentService {
    List<StudentResponseDTO> findAll();
    StudentResponseDTO findById(Long id);
    StudentResponseDTO create(CreateStudentRequest request);
    StudentResponseDTO fullUpdate(Long id, UpdateStudentRequest request);
    StudentResponseDTO partialUpdate(Long id, UpdateStudentRequest request);
    void delete(Long id);
}
