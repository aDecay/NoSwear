package com.noswear.noswear.repository

import com.noswear.noswear.domain.Ratio
import com.noswear.noswear.domain.RatioId
import org.springframework.data.jpa.repository.JpaRepository

interface RatioRepository: JpaRepository<Ratio, RatioId> {
}