package com.noswear.noswear.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TeacherDto (
    val email: String,
    val password: String,
    val name: String,
    val create: Boolean,
    val code: String?,
    val className: String
)