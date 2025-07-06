package com.FealtyX_assignment.Student.repositories;

import com.FealtyX_assignment.Student.models.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentRepository {
    Student save(Student student);
    Optional<Student> findById(Integer id);
    List<Student> findAll();
    void deleteById(Integer id);
    boolean existsById(Integer id);
    Optional<Student> findByEmail(String email);
    void deleteAll();
}
