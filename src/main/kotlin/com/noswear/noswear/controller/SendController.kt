package com.noswear.noswear.controller

import com.noswear.noswear.dto.RecordDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SendController {
    @PostMapping("/send")
    fun send(@RequestBody record: RecordDto) {
        val data = record.data
        for (x in data) {
            print(x)
        }
    }
}