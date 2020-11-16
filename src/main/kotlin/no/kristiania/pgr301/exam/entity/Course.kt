package no.kristiania.pgr301.exam.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class Course(

        @get:Id
        @get:NotBlank
        var courseCode: String? = null,

        @get:NotBlank
        var courseName: String? = null
)