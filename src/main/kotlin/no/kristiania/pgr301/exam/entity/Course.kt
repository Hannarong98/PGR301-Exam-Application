package no.kristiania.pgr301.exam.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class Course(

        @get:Id
        @get:NotBlank
        var courseCode: String? = null,

        @get:NotBlank
        var courseName: String? = null
)