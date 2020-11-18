package no.kristiania.pgr301.exam.dto

class ExamResultDto(

        var courseCode: String? = null,

        var courseName: String? = null,

        var grade: Grade? = null,

        var timeSpentOnExamHrs: Double? = 0.0,

        var attempts: Int? = 0,

        var timeSpentOnCourseHrs: Int? = 0,

        var id: Long? = null

)