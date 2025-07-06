package com.FealtyX_assignment.Student.repositories.impl;

import com.FealtyX_assignment.Student.exceptions.StudentNotFoundException;
import com.FealtyX_assignment.Student.models.Student;
import com.FealtyX_assignment.Student.repositories.IStudentRepository;
import com.FealtyX_assignment.Student.utils.HelperMethods;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.FealtyX_assignment.Student.utils.Constants.*;
import static com.FealtyX_assignment.Student.utils.HelperMethods.*;

@Repository
public class StudentRepository implements IStudentRepository {

    private final Map<Integer, Student> studentStore = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    /**
     * Saves the given student entity.
     *
     * @param student the student entity to be saved
     * @return the saved student entity
     */
    @Override
    public Student save(Student student) {
        if( isEmpty(student) ) {
            throw new IllegalArgumentException(STUDENT_NOT_NULL);
        }
        if ( isEmpty(student.getId()) ){
            Student newStudent = Student.builder()
                .id(idGenerator.getAndIncrement())
                .name(student.getName())
                .age(student.getAge())
                .email(student.getEmail())
                .createdAt(DEFAULT_DATE_TIME)
                .updatedAt(DEFAULT_DATE_TIME)
                .build();
            studentStore.put(newStudent.getId(), newStudent);
            return newStudent;
        } else {
            Student existingStudent = studentStore.get(student.getId());
            if (!isEmpty(existingStudent)) {
                Student updatedStudent = Student.builder()
                    .id(student.getId())
                    .name(student.getName())
                    .age(student.getAge())
                    .email(student.getEmail())
                    .createdAt(existingStudent.getCreatedAt())
                    .updatedAt(DEFAULT_DATE_TIME)
                    .build();
                studentStore.put(updatedStudent.getId(), updatedStudent);
                return updatedStudent;
            } else {
                Student newStudent = Student.builder()
                    .id(student.getId())
                    .name(student.getName())
                    .age(student.getAge())
                    .email(student.getEmail())
                    .createdAt(DEFAULT_DATE_TIME)
                    .updatedAt(DEFAULT_DATE_TIME)
                    .build();
                studentStore.put(newStudent.getId(), newStudent);
                return newStudent;
            }
        }
    }

    /**
     * Finds a student by their unique ID.
     *
     * @param id the unique identifier of the student
     * @return an Optional containing the student if found, or an empty Optional if not found
     */
    @Override
    public Optional<Student> findById(Integer id) {
        if (isEmpty(id)) {return Optional.empty();}
        return Optional.ofNullable(studentStore.get(id));
    }

    /**
     * Retrieves all student entities from the repository.
     *
     * @return a list containing all student entities
     */
    @Override
    public List<Student> findAll() {
        return new ArrayList<>(studentStore.values());
    }

    /**
     * Retrieves all student entities.
     *
     * @return a list of all student entities
     */
    @Override
    public void deleteById(Integer id) {
        if (isEmpty(id)) {
            throw new IllegalArgumentException(STUDENT_ID_NOT_NULL);
        }

        if (isEmpty(studentStore.remove(id))) {
            throw new StudentNotFoundException(STUDENT_NOT_FOUND + id);
        }
    }

    /**
     * Checks if a student exists by their unique ID.
     *
     * @param id the unique identifier of the student
     * @return true if a student with the given ID exists, false otherwise
     */
    @Override
    public boolean existsById(Integer id) {
        if (isEmpty(id)) {
            return false;
        }
        return studentStore.containsKey(id);
    }

    /**
     * Finds a student by their email address.
     *
     * @param email the email address of the student
     * @return an Optional containing the student if found, or an empty Optional if not found
     */
    @Override
    public Optional<Student> findByEmail(String email) {
        if (isEmpty(email) || email.trim().isEmpty()) {
            return OPTIONAL_EMPTY;
        }

        return studentStore.values().stream()
                .filter(student -> email.equals(student.getEmail()))
                .findFirst();
    }

    /**
     * Deletes all student entities from the repository.
     */
    @Override
    public void deleteAll() {
        studentStore.clear();
    }
}
