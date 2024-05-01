package com.noswear.noswear.service

import com.noswear.noswear.dto.StatisticsDto
import com.noswear.noswear.repository.FrequencyRepository
import org.springframework.stereotype.Service

@Service
class StatisticsService(
    private val frequencyRepository: FrequencyRepository
) {
    fun findAll(): List<StatisticsDto> {
        return frequencyRepository.findAll()
            .map { StatisticsDto(
                word = it.frequencyId.word,
                frequency = it.frequency
            ) }.toList()
    }
}