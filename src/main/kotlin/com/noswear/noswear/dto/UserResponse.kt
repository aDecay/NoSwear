package com.noswear.noswear.dto

import com.noswear.noswear.domain.User

data class UserResponse(
    val name: String,
    val email: String
) {
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                user.name,
                user.email
            )
        }
    }

}
