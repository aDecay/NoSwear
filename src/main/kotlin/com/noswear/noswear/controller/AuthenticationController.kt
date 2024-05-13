package com.noswear.noswear.controller

import com.noswear.noswear.domain.Student
import com.noswear.noswear.domain.Teacher
import com.noswear.noswear.dto.StudentDto
import com.noswear.noswear.dto.TeacherDto
import com.noswear.noswear.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthenticationController(
    val authenticationService: AuthenticationService
) {
    @PostMapping("/signup/teacher")
    fun teacherRegister(@RequestBody teacherDto: TeacherDto): ResponseEntity<Teacher> {
        val teacher = authenticationService.teacherRegister(teacherDto)

        return ResponseEntity.ok(teacher)
    }

    @PostMapping("/signup/student")
    fun studentRegister(@RequestBody studentDto: StudentDto): ResponseEntity<Student> {
        val student = authenticationService.studentRegister(studentDto)

        return ResponseEntity.ok(student)
    }
}