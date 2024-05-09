package com.noswear.noswear.controller

import com.noswear.noswear.domain.User
import com.noswear.noswear.dto.RegisterDto
import com.noswear.noswear.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController (
    private val authService: AuthService
) {
    @PostMapping("/join")
    fun join(@RequestBody registerDto: RegisterDto): ResponseEntity<User> {
        return ResponseEntity.ok(authService.join(registerDto))
    }
}