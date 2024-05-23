package com.noswear.noswear.controller

import com.noswear.noswear.dto.DailyCountResponse
import com.noswear.noswear.dto.SendDto
import com.noswear.noswear.dto.TotalCountResponse
import com.noswear.noswear.dto.WordCountResult
import com.noswear.noswear.service.StatisticsService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

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

    @GetMapping("/count/total")
    @PreAuthorize("hasRole('STUDENT')")
    fun getTotalCount(date: LocalDate): ResponseEntity<TotalCountResponse> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val totalCount = statisticsService.getTotalCount(name, date)
        return ResponseEntity.ok(TotalCountResponse.of(totalCount))
    }

    @GetMapping("/count/word")
    @PreAuthorize("hasRole('STUDENT')")
    fun getWordCount(date: LocalDate): ResponseEntity<List<WordCountResult>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val result = mutableListOf<WordCountResult>()
        statisticsService.getWordCount(name, date).map { wordCount ->
            result.add(
                WordCountResult(
                    word = wordCount.wordCountId.word,
                    count = wordCount.count
                )
            )
        }

        return ResponseEntity.ok(result)
    }

    @GetMapping("/count/word/group")
    @PreAuthorize("isAuthenticated()")
    fun getGroupWordCount(programName: String): ResponseEntity<List<WordCountResult>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val result = statisticsService.getGroupWordCount(name, programName).map { wordCountResultVo ->
            WordCountResult.of(wordCountResultVo)
        }

        return ResponseEntity.ok(result)
    }

    @GetMapping("count/daily")
    @PreAuthorize("hasRole('STUDENT')")
    fun getDailyCount(programName: String): ResponseEntity<DailyCountResponse> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val result = statisticsService.getDailyCount(name, programName)
        return ResponseEntity.ok(result)
    }

    @GetMapping("count/daily/group")
    @PreAuthorize("isAuthenticated()")
    fun getGroupDailyCount(programName: String): ResponseEntity<DailyCountResponse> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val result = statisticsService.getGroupDailyCount(name, programName)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/rank")
    @PreAuthorize("hasRole('STUDENT')")
    fun getMyRank(programName: String): ResponseEntity<Int> {
        val authentication = SecurityContextHolder.getContext().authentication
        val name = authentication.name

        val rank = statisticsService.getMyRank(name, programName)
        return ResponseEntity.ok(rank)
    }
}