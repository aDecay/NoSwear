package com.noswear.noswear.service

import com.noswear.noswear.repository.FrequencyRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader

private val logger = KotlinLogging.logger {}

@Service
class SendService (
    private val frequencyRepository: FrequencyRepository
) {
    fun processFile(filename: String) {
        val processBuilder = ProcessBuilder("whisper", filename, "--language", "Korean")
        val process = processBuilder.start()
        BufferedReader(InputStreamReader(process.inputStream))
            .forEachLine { line ->
                logger.debug(line)
            }
    }
}