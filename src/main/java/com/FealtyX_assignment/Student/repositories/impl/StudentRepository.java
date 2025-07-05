package com.FealtyX_assignment.Student.repositories.impl;

import com.FealtyX_assignment.Student.models.Student;
import com.FealtyX_assignment.Student.repositories.IStudentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository implements IStudentRepository {

    /**
     * Saves the given student entity.
     *
     * @param student the student entity to be saved
     * @return the saved student entity
     */
    @Override
    public Student save(Student student) {
        return null;
    }

    /**
     * Finds a student by their unique ID.
     *
     * @param id the unique identifier of the student
     * @return an Optional containing the student if found, or an empty Optional if not found
     */
    @Override
    public Optional<Student> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        return List.of();
    }

    /**
     * Retrieves all student entities.
     *
     * @return a list of all student entities
     */
    @Override
    public void deleteById(Integer id) {

    }

    /**
     * Checks if a student exists by their unique ID.
     *
     * @param id the unique identifier of the student
     * @return true if a student with the given ID exists, false otherwise
     */
    @Override
    public boolean existsById(Integer id) {
        return false;
    }
}
