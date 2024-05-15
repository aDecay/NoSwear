package com.noswear.noswear.repository

import com.noswear.noswear.domain.Program
import org.springframework.data.jpa.repository.JpaRepository

interface ProgramRepository : JpaRepository<Program, Int> {
}