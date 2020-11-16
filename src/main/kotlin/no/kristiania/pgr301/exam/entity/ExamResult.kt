package no.kristiania.pgr301.exam.db

import no.kristiania.pgr301.exam.dto.Grade
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class ExamResult (

        @get:NotNull
        @get:ManyToOne
        var student: Student? = null,

        @get:NotBlank
        var courseCode: String? = null,

        @get:NotBlank
        var courseName: String? = null,

        @get:NotNull
        var courseId: Long? = null,

        @get:NotNull
        var grade: Grade? = null,

        @get:NotNull
        @get:Min(0)
        var timeSpentOnCourse: Int? = 0,

        @get:Id
        @get:GeneratedValue
        var resultId: Long? = null

)