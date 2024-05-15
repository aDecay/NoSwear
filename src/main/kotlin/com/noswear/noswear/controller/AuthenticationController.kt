package com.noswear.noswear.controller

import com.noswear.noswear.domain.User
import com.noswear.noswear.dto.LoginDto
import com.noswear.noswear.dto.LoginResponse
import com.noswear.noswear.dto.StudentDto
import com.noswear.noswear.dto.TeacherDto
import com.noswear.noswear.security.JwtService
import com.noswear.noswear.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthenticationController(
    val authenticationService: AuthenticationService,
    val jwtService: JwtService
) {
    @PostMapping("/signup/teacher")
    fun teacherRegister(@RequestBody teacherDto: TeacherDto): ResponseEntity<User> {
        val teacher = authenticationService.teacherRegister(teacherDto)

        return ResponseEntity.ok(teacher)
    }

    @PostMapping("/signup/student")
    fun studentRegister(@RequestBody studentDto: StudentDto): ResponseEntity<User> {
        val student = authenticationService.studentRegister(studentDto)

        return ResponseEntity.ok(student)
    }

    @PostMapping("/login")
    fun authenticate(@RequestBody loginDto: LoginDto): ResponseEntity<LoginResponse> {
        val authenticatedUser = authenticationService.authenticate(loginDto)
        val jwtToken = jwtService.generateToken(authenticatedUser)
        val loginResponse = LoginResponse(jwtToken, jwtService.jwtExpiration, authenticatedUser.role)

        return ResponseEntity.ok(loginResponse)
    }
}