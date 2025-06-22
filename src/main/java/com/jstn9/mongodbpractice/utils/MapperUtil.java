package com.jstn9.mongodbpractice.utils;

import com.jstn9.mongodbpractice.dto.StudentDTO;
import com.jstn9.mongodbpractice.models.Student;
import lombok.Getter;
import org.modelmapper.ModelMapper;

public class MapperUtil {

    @Getter
    private static final ModelMapper mapper = new ModelMapper();

    public static StudentDTO maptoDto(Student student) {
        return mapper.map(student, StudentDTO.class);
    }

    public static Student mapToEntity(StudentDTO studentDTO){
        return mapper.map(studentDTO, Student.class);
    }
}
