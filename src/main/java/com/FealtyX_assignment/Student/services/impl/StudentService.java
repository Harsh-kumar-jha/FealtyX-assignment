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

import static com.FealtyX_assignment.Student.utils.Constants.EMAIL_ALREADY_EXISTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {

    private final IStudentRepository studentRepository;

    /**
     * Creates a new student in the In-memory DB which is ConcurrentHashMap.
     *
     * @param studentRequestDTO the details of the student to create
     * @return a StudentResponseDTO containing the details of the created student
     * @throws DuplicateEmailException if a student with the same email already exists
     */
    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        log.info("Creating new student with email: {}", studentRequestDTO.getEmail());
        
        if (studentRepository.findByEmail(studentRequestDTO.getEmail()).isPresent()) {
            log.error("Failed to create student: Email already exists: {}", studentRequestDTO.getEmail());
            throw new DuplicateEmailException(EMAIL_ALREADY_EXISTS + studentRequestDTO.getEmail());
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

    /**
     * Retrieves a student with the specified ID from the database.
     *
     * @param id the ID of the student to retrieve
     * @return a StudentResponseDTO containing the details of the retrieved student
     * @throws StudentNotFoundException if no student with the specified ID exists
     */
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

    /**
     * Retrieves a list of all students from the database.
     *
     * @return a list of StudentResponseDTO objects representing all students
     */
    @Override
    public List<StudentResponseDTO> getAllStudents() {
        log.info("Fetching all students");
        List<Student> students = studentRepository.findAll();
        log.debug("Retrieved {} students", students.size());
        
        return students.stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    /**
     * Updates the details of an existing student with the specified ID.
     *
     * @param id the ID of the student to update
     * @param studentRequestDTO the new details for the student
     * @return a StudentResponseDTO containing the updated details of the student
     * @throws StudentNotFoundException if no student with the specified ID exists
     * @throws DuplicateEmailException if the new email address is already in use by another student
     */
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
            throw new DuplicateEmailException(EMAIL_ALREADY_EXISTS + studentRequestDTO.getEmail());
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

    /**
     * Deletes a student with the specified ID from the database.
     *
     * @param id the ID of the student to delete
     * @throws StudentNotFoundException if no student with the specified ID exists
     */
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

    /**
     * Deletes all students from the database.
     *
     * This method removes all student records from the database and logs the operation.
     * It should be used with caution as it will permanently delete all data.
     */
    @Override
    public void deleteAllStudents() {
        log.warn("Deleting all students from the database");
        studentRepository.deleteAll();
        log.info("Successfully deleted all students");
    }

    /**
     * Converts a Student entity to a StudentResponseDTO.
     *
     * @param student the Student entity to convert
     * @return a StudentResponseDTO containing the details of the given Student entity
     */
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
