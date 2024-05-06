package com.noswear.noswear.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ReadDto (
    val id: Int? = null,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val startDate: LocalDate,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val endDate: LocalDate
)