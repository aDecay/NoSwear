package com.noswear.noswear.controller

import com.noswear.noswear.dto.SendDto
import com.noswear.noswear.service.StatisticsService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/statistics")
@Controller
class StatisticsController(
    private val statisticsService: StatisticsService
) {
    @PostMapping("/send")
    @PreAuthorize("hasRole('STUDENT')")
    fun analyzeProfanity(@RequestBody sendDto: SendDto): ResponseEntity<Map<String, Int>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val result = statisticsService.analyzeProfanity(name, sendDto)
        return ResponseEntity.ok(result)
    }
}