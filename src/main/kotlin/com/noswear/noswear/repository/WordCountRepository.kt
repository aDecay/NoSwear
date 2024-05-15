package com.noswear.noswear.repository

import com.noswear.noswear.domain.WordCount
import com.noswear.noswear.domain.WordCountId
import org.springframework.data.jpa.repository.JpaRepository

interface WordCountRepository : JpaRepository<WordCount, WordCountId> {
}