package com.noswear.noswear.controller

import com.noswear.noswear.dto.StatisticsDto
import com.noswear.noswear.service.StatisticsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticsController(
    private val statisticsService: StatisticsService
) {
    @GetMapping("/statistics")
    fun statistics(): List<StatisticsDto> {
        return statisticsService.findAll()
    }
}