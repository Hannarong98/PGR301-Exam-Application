package no.kristiania.pgr301.exam.db

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
class User (
        @get:Id
        @get:NotBlank
        var userId: String? = null
)