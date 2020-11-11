package no.kristiania.pgr301.exam.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
data class Originator(

        @field:NotNull
        var fullName: String,

        @field:Id
        @field:GeneratedValue
        var id: Long? = null
)