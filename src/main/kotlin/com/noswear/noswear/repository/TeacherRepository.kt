package com.noswear.noswear.repository

import com.noswear.noswear.domain.Teacher
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository : JpaRepository<Teacher, Int> {
}