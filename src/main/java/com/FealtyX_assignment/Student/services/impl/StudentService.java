package com.FealtyX_assignment.Student.services.impl;

import com.FealtyX_assignment.Student.dtos.StudentRequestDTO;
import com.FealtyX_assignment.Student.dtos.StudentResponseDTO;
import com.FealtyX_assignment.Student.exceptions.DuplicateEmailException;
import com.FealtyX_assignment.Student.exceptions.StudentNotFoundException;
import com.FealtyX_assignment.Student.models.Student;
import com.FealtyX_assignment.Student.repositories.IStudentRepository;
import com.FealtyX_assignment.Student.services.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {

    private final IStudentRepository studentRepository;

    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        log.info("Creating new student with email: {}", studentRequestDTO.getEmail());
        
        if (studentRepository.findByEmail(studentRequestDTO.getEmail()).isPresent()) {
            log.error("Failed to create student: Email already exists: {}", studentRequestDTO.getEmail());
            throw new DuplicateEmailException("Email already exists: " + studentRequestDTO.getEmail());
        }

        Student student = Student.builder()
                .name(studentRequestDTO.getName())
                .age(studentRequestDTO.getAge())
                .email(studentRequestDTO.getEmail())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Student savedStudent = studentRepository.save(student);
        log.info("Successfully created student with ID: {}", savedStudent.getId());
        
        return convertToResponseDTO(savedStudent);
    }

    @Override
    public StudentResponseDTO getStudentById(Integer id) {
        log.info("Fetching student with ID: {}", id);
        
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found with ID: {}", id);
                    return new StudentNotFoundException("Student not found with id: " + id);
                });
                
        log.debug("Retrieved student: {}", student);
        return convertToResponseDTO(student);
    }

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        log.info("Fetching all students");
        List<Student> students = studentRepository.findAll();
        log.debug("Retrieved {} students", students.size());
        
        return students.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDTO updateStudent(Integer id, StudentRequestDTO studentRequestDTO) {
        log.info("Updating student with ID: {}", id);
        
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Failed to update: Student not found with ID: {}", id);
                    return new StudentNotFoundException("Student not found with id: " + id);
                });

        if (!existingStudent.getEmail().equals(studentRequestDTO.getEmail()) &&
                studentRepository.findByEmail(studentRequestDTO.getEmail()).isPresent()) {
            log.error("Failed to update student {}: Email {} already exists", id, studentRequestDTO.getEmail());
            throw new DuplicateEmailException("Email already exists: " + studentRequestDTO.getEmail());
        }

        log.debug("Updating student {} details - Old: {}, New: {}", id, existingStudent, studentRequestDTO);
        
        existingStudent.setName(studentRequestDTO.getName());
        existingStudent.setAge(studentRequestDTO.getAge());
        existingStudent.setEmail(studentRequestDTO.getEmail());
        existingStudent.setUpdatedAt(LocalDateTime.now());

        Student updatedStudent = studentRepository.save(existingStudent);
        log.info("Successfully updated student with ID: {}", id);
        
        return convertToResponseDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(Integer id) {
        log.info("Attempting to delete student with ID: {}", id);
        
        if (!studentRepository.existsById(id)) {
            log.error("Failed to delete: Student not found with ID: {}", id);
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        
        studentRepository.deleteById(id);
        log.info("Successfully deleted student with ID: {}", id);
    }

    @Override
    public void deleteAllStudents() {
        log.warn("Deleting all students from the database");
        studentRepository.deleteAll();
        log.info("Successfully deleted all students");
    }

    private StudentResponseDTO convertToResponseDTO(Student student) {
        log.trace("Converting student entity to DTO: {}", student);
        return StudentResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .age(student.getAge())
                .email(student.getEmail())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
