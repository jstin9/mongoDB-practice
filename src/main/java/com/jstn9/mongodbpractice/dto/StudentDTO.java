package com.jstn9.mongodbpractice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentDTO {

    @NotBlank(message = "First name is required and cannot be empty.")
    private String firstName;

    @NotBlank(message = "Last name is required and cannot be empty.")
    private String lastName;

    @Min(value = 16, message = "Age must be at least 16.")
    @Max(value = 65, message = "Age must be no more than 65.")
    private int age;

    @NotBlank(message = "Faculty is required and cannot be empty.")
    private String faculty;

    @Min(value = 1, message = "Course must be at least 1.")
    @Max(value = 6, message = "Course must be no more than 6.")
    private int course;

    @Positive(message = "Group number must be a positive number.")
    private int group;

    @NotNull(message = "Scholarship status must be provided (true or false).")
    private Boolean scholarship;
}
