package no.kristiania.pgr301.exam.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
class User (
        @get:Id
        @get:NotBlank
        var userId: String? = null
)