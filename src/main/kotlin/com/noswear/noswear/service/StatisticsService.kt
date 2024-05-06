package com.noswear.noswear.service

import com.noswear.noswear.domain.FrequencyResult
import com.noswear.noswear.domain.RatioResult
import com.noswear.noswear.repository.FrequencyRepository
import com.noswear.noswear.repository.RatioRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class StatisticsService(
    private val ratioRepository: RatioRepository,
    private val frequencyRepository: FrequencyRepository
) {
    fun ratioById(id: Int, startDate: LocalDate, endDate: LocalDate): RatioResult {
        return ratioRepository.findSumById(id, startDate, endDate)
    }

    fun ratioAll(startDate: LocalDate, endDate: LocalDate): RatioResult {
        return ratioRepository.findSum(startDate, endDate)
    }

    fun perWordById(id: Int, startDate: LocalDate, endDate: LocalDate, size: Int): List<FrequencyResult> {
        return frequencyRepository.findGroupByFrequencyResultById(id, startDate, endDate, PageRequest.ofSize(size))
    }

    fun perWordAll(startDate: LocalDate, endDate: LocalDate, size: Int): List<FrequencyResult> {
        return frequencyRepository.findGroupByFrequencyResult(startDate, endDate, PageRequest.ofSize(size))
    }
}