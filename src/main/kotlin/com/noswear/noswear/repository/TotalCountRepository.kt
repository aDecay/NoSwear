package com.noswear.noswear.repository

import com.noswear.noswear.domain.TotalCount
import com.noswear.noswear.domain.TotalCountId
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface TotalCountRepository : JpaRepository<TotalCount, TotalCountId> {
    fun findByTotalCountIdIdAndTotalCountIdDate(id: Int, date: LocalDate): List<TotalCount>
}