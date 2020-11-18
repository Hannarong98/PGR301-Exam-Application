package no.kristiania.pgr301.exam.converter

import no.kristiania.pgr301.exam.entity.ExamResult
import no.kristiania.pgr301.exam.dto.ExamResultDto

object DtoConvertExamResult {

    fun transform(examResult: ExamResult) : ExamResultDto {
        return ExamResultDto().apply {
            courseCode = examResult.courseCode
            courseName = examResult.courseName
            attempts = examResult.attempts
            grade = examResult.grade
            timeSpentOnCourseHrs = examResult.timeSpentOnCourseHrs
            timeSpentOnExamHrs = examResult.timeSpentOnExamHrs
        }
    }

    fun transform(examResults: Iterable<ExamResult>) : List<ExamResultDto> = examResults.map { transform(it) }
}