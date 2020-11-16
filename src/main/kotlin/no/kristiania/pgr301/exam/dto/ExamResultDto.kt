package no.kristiania.pgr301.exam.dto

class ExamResultDto(

        var courseCode: String? = null,

        var courseName: String? = null,

        var grade: Grade? = null,

        var timeSpentOnCourse: Int? = 0,

        var id: Long? = null

)