package com.noswear.noswear.repository

import com.noswear.noswear.domain.Ratio
import com.noswear.noswear.domain.RatioId
import com.noswear.noswear.domain.RatioResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface RatioRepository: JpaRepository<Ratio, RatioId> {
    @Query(value =
        "SELECT " +
        "new com.noswear.noswear.domain.RatioResult(SUM(r.normal), SUM(r.profanity)) " +
        "FROM Ratio r " +
        "WHERE r.ratioId.date >= :start_date and r.ratioId.date <= :end_date"
    )
    fun findSum(
        @Param("start_date") startDate: LocalDate,
        @Param("end_date") endDate: LocalDate
    ): RatioResult

    @Query(value =
        "SELECT " +
        "new com.noswear.noswear.domain.RatioResult(SUM(r.normal), SUM(r.profanity)) " +
        "FROM Ratio r " +
        "WHERE r.ratioId.date >= :start_date and r.ratioId.date <= :end_date " +
        "and r.ratioId.user.id = :id"
    )
    fun findSumById(
        @Param("id") id: Int,
        @Param("start_date") startDate: LocalDate,
        @Param("end_date") endDate: LocalDate
    ): RatioResult
}