package com.jstn9.mongodbpractice.repositories;

import com.jstn9.mongodbpractice.models.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
}
