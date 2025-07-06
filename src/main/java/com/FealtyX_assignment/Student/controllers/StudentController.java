package com.FealtyX_assignment.Student.controllers;

import com.FealtyX_assignment.Student.dtos.StudentRequestDTO;
import com.FealtyX_assignment.Student.dtos.StudentResponseDTO;
import com.FealtyX_assignment.Student.services.IStudentService;
import com.FealtyX_assignment.Student.services.impl.OllamaService;
import com.FealtyX_assignment.Student.models.StudentSummary;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Validated
public class StudentController {

    private final IStudentService studentService;
    private final OllamaService ollamaService;

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Valid @RequestBody StudentRequestDTO request) {
        log.info("Received request to create student: {}", request);
        StudentResponseDTO response = studentService.createStudent(request);
        log.info("Successfully created student with ID: {}", response.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(
            @PathVariable Integer id) {
        log.info("Received request to get student with ID: {}", id);
        StudentResponseDTO response = studentService.getStudentById(id);
        log.debug("Retrieved student details: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        log.info("Received request to get all students");
        List<StudentResponseDTO> students = studentService.getAllStudents();
        log.debug("Retrieved {} students", students.size());
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable Integer id,
            @Valid @RequestBody StudentRequestDTO request) {
        log.info("Received request to update student with ID: {}", id);
        log.debug("Update details: {}", request);
        StudentResponseDTO response = studentService.updateStudent(id, request);
        log.info("Successfully updated student with ID: {}", id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        log.info("Received request to delete student with ID: {}", id);
        studentService.deleteStudent(id);
        log.info("Successfully deleted student with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllStudents() {
        log.warn("Received request to delete ALL students");
        studentService.deleteAllStudents();
        log.info("Successfully deleted all students");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<StudentSummary> getStudentSummary(@PathVariable Integer id) {
        log.info("Received request to generate summary for student with ID: {}", id);
        
        StudentResponseDTO student = studentService.getStudentById(id);
        log.debug("Retrieved student details for ID {}: {}", id, student);
        
        String summary = ollamaService.generateStudentSummary(student);
        
        StudentSummary studentSummary = StudentSummary.builder()
            .studentId(student.getId())
            .summary(summary)
            .generatedAt(LocalDateTime.now())
            .build();
            
        log.info("Successfully generated summary for student with ID: {}", id);
        log.debug("Generated summary: {}", studentSummary);
        
        return ResponseEntity.ok(studentSummary);
    }
}
