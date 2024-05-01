package com.noswear.noswear.repository

import com.noswear.noswear.domain.Frequency
import com.noswear.noswear.domain.FrequencyId
import org.springframework.data.jpa.repository.JpaRepository

interface FrequencyRepository: JpaRepository<Frequency, FrequencyId> {
}