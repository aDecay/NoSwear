package com.noswear.noswear.repository

import com.noswear.noswear.domain.TotalCount
import com.noswear.noswear.domain.TotalCountId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface TotalCountRepository : JpaRepository<TotalCount, TotalCountId> {
    fun findByTotalCountIdIdAndTotalCountIdDate(id: Int, date: LocalDate): List<TotalCount>
    @Query("WITH ranked AS( " +
            "SELECT RANK() OVER (ORDER BY COALESCE(SUM(count), 0) ASC) AS rank, j.id " +
            "FROM joins j " +
            "LEFT OUTER JOIN " +
            "(SELECT *" +
            "FROM total_count " +
            "WHERE date >= :startDate AND date <= :endDate) t ON t.id = j.id " +
            "WHERE j.program_id = :programId GROUP BY j.id) " +
            "SELECT rank FROM ranked WHERE id=:id",
        nativeQuery = true)
    fun findRankByIdAndProgramIdAndStartDateAndEndDate(id: Int, programId: Int, startDate: LocalDate, endDate: LocalDate): Int
    @Query("SELECT date AS date, SUM(count) AS count " +
            "FROM total_count t " +
            "JOIN joins j " +
            "WHERE t.id = j.id AND j.program_id = :programId AND t.date >= :startDate AND t.date <= :endDate " +
            "GROUP BY t.date ",
        nativeQuery = true)
    fun findGroupDailyCount(programId: Int, startDate: LocalDate, endDate: LocalDate): List<DailyCountVo>

    interface DailyCountVo {
        val date: LocalDate
        val count: Int
    }
}