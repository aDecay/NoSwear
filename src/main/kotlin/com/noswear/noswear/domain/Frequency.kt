package com.noswear.noswear.domain

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Entity
class Frequency (
    @EmbeddedId
    val frequencyId: FrequencyId,
    @Column
    @NotNull
    var count: Int = 1
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (this::class != other::class) {
            return false
        }

        return frequencyId == (other as Frequency).frequencyId
    }

    override fun hashCode() = Objects.hashCode(frequencyId)
}

@Embeddable
data class FrequencyId(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    val user: User,
    @Column(columnDefinition = "DATE")
    val date: LocalDate,
    @Column
    val word: String,
) : Serializable