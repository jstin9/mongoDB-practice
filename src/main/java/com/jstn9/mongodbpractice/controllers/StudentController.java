package com.jstn9.mongodbpractice.controllers;


import com.jstn9.mongodbpractice.dto.StudentDTO;
import com.jstn9.mongodbpractice.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> getStudents(
            @RequestParam(required = false) String faculty,
            @RequestParam(required = false) Integer course,
            @RequestParam(required = false) Boolean scholarship,
            @RequestParam(required = false) Integer group,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String sort
    ) {
        String sortField = null;
        String direction = "asc";

        if (sort != null && sort.contains(",")) {
            String[] parts = sort.split(",");
            sortField = parts[0];
            direction = parts[1];
        }

        return studentService.getStudents(faculty, course, scholarship, group, age, sortField, direction);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable String id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        return  ResponseEntity.ok(studentService.createStudent(studentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable String id, @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentDTO> patchStudent(@PathVariable String id, @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.patchStudent(id, studentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
