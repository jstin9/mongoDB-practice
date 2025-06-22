package com.jstn9.mongodbpractice.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("student")
@Data
@NoArgsConstructor
@EnableMongoAuditing
public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private String faculty;
    private int course;
    private int group;
    private boolean scholarship;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
