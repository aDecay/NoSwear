package com.noswear.noswear.repository

import com.noswear.noswear.domain.Joins
import com.noswear.noswear.domain.JoinsId
import org.springframework.data.jpa.repository.JpaRepository

interface JoinsRepository : JpaRepository<Joins, JoinsId> {
    fun findByJoinsIdUserId(id: Int): List<Joins>
    fun findByJoinsIdProgramClassIdAndJoinsIdProgramProgramName(cId: String, programName: String): List<Joins>
}