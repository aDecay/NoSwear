package com.noswear.noswear.controller

import com.noswear.noswear.dto.RecordDto
import com.noswear.noswear.service.SendService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class SendController (
    private val sendService: SendService
) {
    @PostMapping("/send")
    fun send(@RequestBody record: RecordDto) {
        sendService.analyze(record.id, record.data)
    }
}