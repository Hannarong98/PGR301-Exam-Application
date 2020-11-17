package no.kristiania.pgr301.exam.dto

class ExamResultDto(

        var courseCode: String? = null,

        var courseName: String? = null,

        var grade: Grade? = null,

        var timeSpentOnExam: Int? = 0,

        var timeSpentOnCourse: Int? = 0,

        var id: Long? = null

)