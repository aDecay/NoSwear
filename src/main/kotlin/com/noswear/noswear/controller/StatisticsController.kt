package com.noswear.noswear.controller

import com.noswear.noswear.domain.FrequencyResult
import com.noswear.noswear.domain.RatioResult
import com.noswear.noswear.dto.ReadDto
import com.noswear.noswear.service.StatisticsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/statistics")
class StatisticsController(
    private val statisticsService: StatisticsService
) {
    @GetMapping("/ratio")
    fun ratio(readDto: ReadDto): RatioResult {
        return if (readDto.id == null) {
            statisticsService.ratioAll(readDto.startDate, readDto.endDate)
        } else {
            statisticsService.ratioById(readDto.id, readDto.startDate, readDto.endDate)
        }
    }

    @GetMapping("/most-used")
    fun mostUsed(readDto: ReadDto): String? {
        val resultList = perWord(readDto, 1)
        return if (resultList.isEmpty()) {
            null
        } else {
            resultList[0].word
        }
    }

    @GetMapping("/per-word")
    fun perWord(readDto: ReadDto, @RequestParam size: Int): List<FrequencyResult> {
        return if (readDto.id == null) {
            statisticsService.perWordAll(readDto.startDate, readDto.endDate, size)
        } else {
            statisticsService.perWordById(readDto.id, readDto.startDate, readDto.endDate, size)
        }
    }
}