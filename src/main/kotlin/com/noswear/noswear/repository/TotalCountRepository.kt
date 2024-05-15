package com.noswear.noswear.repository

import com.noswear.noswear.domain.TotalCount
import com.noswear.noswear.domain.TotalCountId
import org.springframework.data.jpa.repository.JpaRepository

interface TotalCountRepository : JpaRepository<TotalCount, TotalCountId> {
}