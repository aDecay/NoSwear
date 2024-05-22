package com.noswear.noswear.repository

import com.noswear.noswear.domain.WordCount
import com.noswear.noswear.domain.WordCountId
import com.noswear.noswear.dto.WordCountResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface WordCountRepository : JpaRepository<WordCount, WordCountId> {
    fun findByWordCountIdIdAndWordCountIdDateOrderByCountDesc(id: Int, date: LocalDate): List<WordCount>
    @Query("SELECT word AS word, SUM(count) AS count " +
            "FROM word_count w " +
            "JOIN joins j " +
            "WHERE w.id = j.id AND j.program_id = :programId AND w.date >= :startDate AND w.date <= :endDate " +
            "GROUP BY w.word " +
            "ORDER BY count DESC",
        nativeQuery = true)
    fun findGroupWordCount(programId: Int, startDate: LocalDate, endDate: LocalDate): List<WordCountResultVo>

    interface WordCountResultVo {
        fun getWord(): String
        fun getCount(): Int
    }
}