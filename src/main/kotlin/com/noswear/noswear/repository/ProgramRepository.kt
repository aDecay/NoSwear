package com.noswear.noswear.repository

import com.noswear.noswear.domain.Program
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProgramRepository : JpaRepository<Program, Int> {
    fun findByClassId(cId: String): List<Program>
    fun findByClassIdAndProgramName(cId: String, programName: String): Program?
    @Query("SELECT p " +
            "FROM Program p " +
            "WHERE p.classId = :cId AND p.startDate <= CURRENT_DATE AND p.endDate >= CURRENT_DATE")
    fun findCurrentProgramByClassId(cId: String): Program?
    @Query("SELECT p " +
            "FROM Program p " +
            "JOIN p.joins j " +
            "WHERE j.joinsId.id = :id AND p.startDate <= CURRENT_DATE AND p.endDate >= CURRENT_DATE")
    fun findCurrentProgramByUserId(id: Int): Program?
}