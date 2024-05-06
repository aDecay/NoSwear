package com.noswear.noswear.service

import com.noswear.noswear.domain.FrequencyResult
import com.noswear.noswear.dto.StatisticsDto
import com.noswear.noswear.repository.FrequencyRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class StatisticsService(
    private val frequencyRepository: FrequencyRepository
) {
    fun findAll(): List<StatisticsDto> {
        return frequencyRepository.findAll()
            .map { frequency ->
                StatisticsDto(
                word = frequency.frequencyId.word,
                frequency = frequency.count
            ) }.toList()
    }

    fun perWordById(id: Int, startDate: LocalDate, endDate: LocalDate, size: Int): List<FrequencyResult> {
        return frequencyRepository.findGroupByFrequencyResultById(id, startDate, endDate, PageRequest.ofSize(size))
    }

    fun perWordAll(startDate: LocalDate, endDate: LocalDate, size: Int): List<FrequencyResult> {
        return frequencyRepository.findGroupByFrequencyResult(startDate, endDate, PageRequest.ofSize(size))
    }
}