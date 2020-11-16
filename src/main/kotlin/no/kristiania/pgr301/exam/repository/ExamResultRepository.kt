package no.kristiania.pgr301.exam.repository

import no.kristiania.pgr301.exam.entity.ExamResult
import no.kristiania.pgr301.exam.entity.Student
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface ExamResultRepository : CrudRepository<ExamResult, Long>{
    fun existsByCourseCodeAndStudent(courseCode: String, student: Student) : Boolean
    fun findAllByStudentStudentId(studentId: String) : List<ExamResult>
}