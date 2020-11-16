package no.kristiania.pgr301.exam.converter

import no.kristiania.pgr301.exam.db.ExamResult
import no.kristiania.pgr301.exam.db.Student
import no.kristiania.pgr301.exam.dto.ExamResultDto
import no.kristiania.pgr301.exam.dto.StudentDto

object DtoConverter {

    fun transform(student: Student) : StudentDto {
        return StudentDto().apply {
            id  = student.id
            firstName = student.firstName
            lastName = student.lastName
            age = student.age
            examResults = student.examResults.map {
                transform(it)
            }.toMutableList()
        }
    }

    fun transform(examResult: ExamResult) : ExamResultDto {
        return ExamResultDto().apply {
            courseCode = examResult.courseCode
            courseName = examResult.courseName
            courseId = examResult.courseId
            grade = examResult.grade
            timeSpentOnCourse = examResult.timeSpentOnCourse
        }
    }

    fun transform(examResults: Iterable<ExamResult>) : List<ExamResultDto> = examResults.map { transform(it) }

}