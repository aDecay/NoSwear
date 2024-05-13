package com.noswear.noswear.repository

import com.noswear.noswear.domain.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository : JpaRepository<Student, Int> {
}