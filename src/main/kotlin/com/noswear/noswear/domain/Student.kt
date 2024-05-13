package com.noswear.noswear.domain

import jakarta.persistence.*
import java.util.*

@Entity
class Student (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    @Column(nullable = false)
    val email: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (this::class != other::class) {
            return false
        }

        return id == (other as Student).id
    }

    override fun hashCode() = Objects.hashCode(id)
}