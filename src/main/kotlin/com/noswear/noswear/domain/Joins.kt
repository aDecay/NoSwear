package com.noswear.noswear.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import java.io.Serializable
import java.util.*

@Entity
class Joins(
    @EmbeddedId
    val joinsId: JoinsId
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (this::class != other::class) {
            return false
        }

        return joinsId == (other as Joins).joinsId
    }

    override fun hashCode() = Objects.hashCode(joinsId)
}

@Embeddable
data class JoinsId(
    @Column(nullable = false)
    val id: Int,
    @Column(nullable = false)
    val pId: Int
) : Serializable