package com.noswear.noswear.controller

import com.noswear.noswear.domain.Frequency
import com.noswear.noswear.domain.FrequencyId
import com.noswear.noswear.dto.RecordDto
import com.noswear.noswear.repository.FrequencyRepository
import mu.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

@RestController
class SendController (
    private val statisticsRepository: FrequencyRepository
) {
    @PostMapping("/send")
    fun send(@RequestBody record: RecordDto): Frequency {
        val date = LocalDate.now().toString()
        val frequencyId = FrequencyId(date, date)

        val data = record.data
        val length = data.length
        logger.debug("length: ${length}, prefix: ${if (length < 10) length else 10}")

        return statisticsRepository.findById(frequencyId)
            .map { frequency ->
                frequency.frequency += 1
                return@map statisticsRepository.save(frequency)
            }.orElseGet {
                statisticsRepository.save(Frequency(frequencyId, 0))
            }
    }
}