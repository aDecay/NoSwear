package com.noswear.noswear.dto

import java.time.LocalDate

data class ProgramDto(
    val programName: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)
