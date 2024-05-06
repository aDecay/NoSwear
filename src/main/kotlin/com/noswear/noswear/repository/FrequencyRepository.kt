package com.noswear.noswear.repository

import com.noswear.noswear.domain.Frequency
import com.noswear.noswear.domain.FrequencyId
import com.noswear.noswear.domain.FrequencyResult
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface FrequencyRepository: JpaRepository<Frequency, FrequencyId> {
    @Query(value =
        "SELECT " +
        "new com.noswear.noswear.domain.FrequencyResult(f.frequencyId.word, SUM(f.count))" +
        "FROM Frequency f " +
        "WHERE f.frequencyId.date >= :start_date and f.frequencyId.date <= :end_date " +
        "GROUP BY f.frequencyId.word " +
        "ORDER BY SUM(f.count) DESC"
    )
    fun findGroupByFrequencyResult(
        @Param("start_date") startDate: LocalDate,
        @Param("end_date") endDate: LocalDate,
        pageRequest: PageRequest
    ): List<FrequencyResult>
    @Query(value =
    "SELECT " +
            "new com.noswear.noswear.domain.FrequencyResult(f.frequencyId.word, SUM(f.count))" +
            "FROM Frequency f " +
            "WHERE f.frequencyId.date >= :start_date and f.frequencyId.date <= :end_date " +
            "and f.frequencyId.user.id = :id " +
            "GROUP BY f.frequencyId.word " +
            "ORDER BY SUM(f.count) DESC"
    )
    fun findGroupByFrequencyResultById(
        @Param("id") id: Int,
        @Param("start_date") startDate: LocalDate,
        @Param("end_date") endDate: LocalDate,
        pageRequest: PageRequest
    ): List<FrequencyResult>
}