package no.kristiania.pgr301.exam.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.Max
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
data class Quote(

        @field:NotNull
        @field:Size(min = 0, max = 500)
        var quote: String,

        @field:NotNull
        var year: Int,

        @field:NotNull
        @field:ManyToOne
        var originator: Originator,

        @field:Id
        @field:GeneratedValue
        var id: Long?  = null

)


