package com.noswear.noswear.repository

import com.noswear.noswear.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {
}