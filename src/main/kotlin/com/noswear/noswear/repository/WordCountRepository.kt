package com.noswear.noswear.repository

import com.noswear.noswear.domain.WordCount
import com.noswear.noswear.domain.WordCountId
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface WordCountRepository : JpaRepository<WordCount, WordCountId> {
    fun findByWordCountIdIdAndWordCountIdDateOrderByCountDesc(id: Int, date: LocalDate): List<WordCount>
}