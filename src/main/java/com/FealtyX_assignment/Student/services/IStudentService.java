package com.FealtyX_assignment.Student.services;

import com.FealtyX_assignment.Student.dtos.StudentRequestDTO;
import com.FealtyX_assignment.Student.dtos.StudentResponseDTO;
import com.FealtyX_assignment.Student.models.StudentSummary;

import java.util.List;

public interface IStudentService {
    StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO);
    StudentResponseDTO getStudentById(Integer id);
    List<StudentResponseDTO> getAllStudents();
    StudentResponseDTO updateStudent(Integer id, StudentRequestDTO studentRequestDTO);
    void deleteStudent(Integer id);
    void deleteAllStudents();
}
