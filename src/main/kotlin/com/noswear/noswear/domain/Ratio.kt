package com.noswear.noswear.domain

import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Entity
class Ratio(
    @EmbeddedId
    val ratioId: RatioId,
    @Column
    var normal: Int = 0,
    @Column
    var profanity: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (this::class != other::class) {
            return false
        }

        return ratioId == (other as Ratio).ratioId
    }

    override fun hashCode() = Objects.hashCode(ratioId)
}

@Embeddable
data class RatioId(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    val user: User,
    @Column(columnDefinition = "DATE")
    val date: LocalDate
) : Serializable