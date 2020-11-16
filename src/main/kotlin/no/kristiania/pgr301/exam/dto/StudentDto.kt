package no.kristiania.pgr301.exam.dto


class StudentDto (

        var firstName: String? = null,

        var lastName: String? = null,

        var age: Int? = null,

        var id: String? = null,

        var examResults: MutableList<ExamResultDto> = mutableListOf()


)