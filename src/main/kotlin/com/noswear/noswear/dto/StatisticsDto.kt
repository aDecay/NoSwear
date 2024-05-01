package com.noswear.noswear.dto

import lombok.Data

@Data
class StatisticsDto (
    val word: String,
    val frequency: Int
)