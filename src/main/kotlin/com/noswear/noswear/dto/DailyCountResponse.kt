package com.noswear.noswear.dto

import com.noswear.noswear.domain.Program
import com.noswear.noswear.repository.TotalCountRepository
import java.time.DayOfWeek

data class DailyCountResponse(
    val raw: List<Map<String, Any>>,
    val min: Int,
    val max: Int,
    val avg: Double
) {
    companion object {
        fun of(dailyCountVoList: List<TotalCountRepository.DailyCountVo>, program: Program): DailyCountResponse {
            var days = 0
            val raw = buildList {
                var date = program.startDate
                while (date <= program.endDate) {
                    val count = dailyCountVoList.find { it.date == date }?.count
                        ?: 0
                    add(
                        mapOf(
                            "date" to date,
                            "count" to count
                        )
                    )
                    do {
                        date = date.plusDays(1)
                    } while (date.dayOfWeek == DayOfWeek.SUNDAY || date.dayOfWeek == DayOfWeek.SATURDAY)
                    days += 1
                }
            }
            return DailyCountResponse(
                raw = raw,
                min = if (dailyCountVoList.size == raw.size)
                    dailyCountVoList.minOf { it.count }
                else
                    0,
                max = if (dailyCountVoList.size == raw.size)
                    dailyCountVoList.maxOf { it.count }
                else
                    0,
                avg = (dailyCountVoList.sumOf { it.count }).toDouble() / days
            )
        }
    }
}