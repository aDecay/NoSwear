package com.noswear.noswear.service

import com.noswear.noswear.domain.User
import com.noswear.noswear.dto.RegisterDto
import com.noswear.noswear.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val userRepository: UserRepository
) {
    fun join(registerDto: RegisterDto): User {
        return userRepository.save(User(
            email = registerDto.email,
            password = registerDto.password,
            nickname = registerDto.nickname
        ))
    }
}