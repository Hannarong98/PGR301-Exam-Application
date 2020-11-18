package no.kristiania.pgr301.exam.entity

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Student(

        @get:NotNull
        var firstName: String? = null,

        @get:NotNull
        var lastName: String? = null,

        @get:NotNull
        var age: Int = 0,

        @get:OneToMany(mappedBy = "student", cascade = [(CascadeType.ALL)])
        var examResults: MutableList<ExamResult> = mutableListOf(),

        @get:Id
        @get:NotNull
        var studentId: String? = null
)