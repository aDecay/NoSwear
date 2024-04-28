package com.noswear.noswear.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticsController {
    @GetMapping("/statistics")
    fun statistics(): List<Map<String, Any>> {
        val result = mutableListOf<Map<String, Any>>()
        result.add(mutableMapOf<String, Any>("test1" to 5))
        result.add(mutableMapOf<String, Any>("test2" to 10))
        return result
    }
}