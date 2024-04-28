package com.noswear.noswear.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticsController {
    @GetMapping("/statistics")
    fun statistics(): List<Map<String, Any>> {
        val result = mutableListOf<Map<String, Any>>()
        result.add(mutableMapOf<String, Any>("word" to "test1", "frequency" to 5))
        result.add(mutableMapOf<String, Any>("word" to "test2", "frequency" to 10))
        return result
    }
}