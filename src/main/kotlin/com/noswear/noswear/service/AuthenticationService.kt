package com.noswear.noswear.service

import com.noswear.noswear.domain.Student
import com.noswear.noswear.domain.Teacher
import com.noswear.noswear.dto.StudentDto
import com.noswear.noswear.dto.TeacherDto
import com.noswear.noswear.repository.StudentRepository
import com.noswear.noswear.repository.TeacherRepository
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    val teacherRepository: TeacherRepository,
    val studentRepository: StudentRepository
) {
    fun teacherRegister(teacherDto: TeacherDto): Teacher {
        val teacher = Teacher(
            email = teacherDto.email,
            password = teacherDto.password,
            name = teacherDto.name
        )

        return teacherRepository.save(teacher)
    }

    fun studentRegister(studentDto: StudentDto): Student {
        val student = Student(
            email = studentDto.email,
            password = studentDto.password,
            name = studentDto.name
        )

        return studentRepository.save(student)
    }
}