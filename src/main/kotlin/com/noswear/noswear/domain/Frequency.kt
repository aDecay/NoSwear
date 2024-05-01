package com.noswear.noswear.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import java.io.Serializable
import java.util.Objects

@Entity
class Frequency(
    frequencyId: FrequencyId,
    frequency: Int
) {
    @EmbeddedId
    var frequencyId = frequencyId
        protected set
    @Column
    var frequency = frequency
        protected set

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
    @Column
    val id: String,
    @Column
    val word: String
) : Serializable