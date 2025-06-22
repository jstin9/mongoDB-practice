package com.jstn9.mongodbpractice.services;

import com.jstn9.mongodbpractice.dto.StudentDTO;
import com.jstn9.mongodbpractice.models.Student;
import com.jstn9.mongodbpractice.repositories.StudentRepository;
import com.jstn9.mongodbpractice.utils.MapperUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final MongoTemplate mongoTemplate;

    public StudentService(StudentRepository studentRepository, MongoTemplate mongoTemplate) {
        this.studentRepository = studentRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<StudentDTO> getStudents(String faculty, Integer course, Boolean scholarship,
                                        Integer group, Integer age, String sortField, String sortDirection) {
        Query query = new Query();

        if (faculty != null) {
            query.addCriteria(Criteria.where("faculty").is(faculty));
        }
        if (course != null) {
            query.addCriteria(Criteria.where("course").is(course));
        }
        if (scholarship != null) {
            query.addCriteria(Criteria.where("scholarship").is(scholarship));
        }
        if (group != null) {
            query.addCriteria(Criteria.where("group").is(group));
        }
        if (age != null) {
            query.addCriteria(Criteria.where("age").is(age));
        }

        if (sortField != null && !sortField.isEmpty()) {
            Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
            query.with(Sort.by(direction, sortField));
        }

        return mongoTemplate.find(query, Student.class)
                .stream()
                .map(MapperUtil::maptoDto)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(String id) {
        Student student = findByIdOrThrow(id);
        return MapperUtil.maptoDto(student);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = MapperUtil.mapToEntity(studentDTO);
        return MapperUtil.maptoDto(studentRepository.save(student));
    }

    public StudentDTO updateStudent(String id, StudentDTO studentDTO) {
        Student student = findByIdOrThrow(id);
        MapperUtil.getMapper().map(studentDTO, student);
        Student updatedStudent = studentRepository.save(student);
        return MapperUtil.maptoDto(updatedStudent);
    }

    public StudentDTO patchStudent(String id, StudentDTO studentDTO) {
        Student student = findByIdOrThrow(id);
        MapperUtil.getMapper().map(studentDTO, student);
        Student updatedStudent = studentRepository.save(student);
        return MapperUtil.maptoDto(updatedStudent);
    }

    public void deleteStudent(String id) {
        Student student = findByIdOrThrow(id);
        studentRepository.delete(student);
    }

    private Student findByIdOrThrow(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found with id: " + id));
    }
}
