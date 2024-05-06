package com.noswear.noswear.domain

data class RatioResult (
    private val nullableNormal: Long?,
    private val nullableProfanity: Long?
) {
    val normal: Long
        get() = nullableNormal ?: 0
    val profanity: Long
        get() = nullableProfanity ?: 0
}