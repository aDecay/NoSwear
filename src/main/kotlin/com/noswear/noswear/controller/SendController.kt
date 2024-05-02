package com.noswear.noswear.controller

import com.noswear.noswear.domain.Frequency
import com.noswear.noswear.domain.FrequencyId
import com.noswear.noswear.dto.RecordDto
import com.noswear.noswear.repository.FrequencyRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class SendController (
    private val statisticsRepository: FrequencyRepository
) {
    @PostMapping("/send")
    fun send(@RequestBody record: RecordDto): Frequency {
        val date = LocalDate.now().toString()
        val frequencyId = FrequencyId(date, date)

        val data = record.data
        for (x in data) {
            print(x)
        }
        println()

        return statisticsRepository.findById(frequencyId)
            .map { frequency ->
                frequency.frequency += 1
                return@map statisticsRepository.save(frequency)
            }.orElseGet {
                statisticsRepository.save(Frequency(frequencyId, 0))
            }
    }
}