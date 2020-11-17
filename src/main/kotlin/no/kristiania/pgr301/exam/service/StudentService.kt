package no.kristiania.pgr301.exam.service

import com.github.javafaker.Faker
import no.kristiania.pgr301.exam.entity.ExamResult
import no.kristiania.pgr301.exam.entity.Student
import no.kristiania.pgr301.exam.dto.Grade
import no.kristiania.pgr301.exam.repository.CourseRepository
import no.kristiania.pgr301.exam.repository.ExamResultRepository
import no.kristiania.pgr301.exam.repository.StudentRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

/*
* TODO
* replace exceptions with logger
* metrics
* */

@Service
class StudentService (
        private val studentRepository: StudentRepository,
        private val courseRepository: CourseRepository,
        private val examResultRepository: ExamResultRepository
) {
    private val faker = Faker()

    fun findByIdEager(studentId: String): Student? {
        val student = studentRepository.findById(studentId).orElse(null)

        student?.examResults?.size

        return student
    }

    fun registerStudent(studentId: String): Boolean {
        if (studentRepository.existsById(studentId)) {
            return false
        }

        val age = (19..35).random()

        val student = Student()
        student.also {
            it.studentId = studentId
            it.firstName = faker.elderScrolls().firstName()
            it.lastName = faker.elderScrolls().lastName()
            it.age = age
        }
        studentRepository.save(student)
        return true
    }

    private fun validateIfExamTaken(courseId: String, student: Student): Boolean {
        return examResultRepository.existsByCourseCodeAndStudent(courseId, student)
    }

    private fun getRandomGrade() : Grade{
        return when((0..7).random()){
            1-> Grade.F
            2-> Grade.E
            3-> Grade.D
            4-> Grade.C
            5-> Grade.B
            else -> Grade.A
        }
    }

    fun takeExam(studentId: String, courseId: String): ExamResult? {

        val student = studentRepository.findByStudentId(studentId)!!
        val course = courseRepository.findByCourseCode(courseId)!!

        if(validateIfExamTaken(courseId, student)) return null

        val examResult = ExamResult()
        examResult.also {
            it.courseCode = course.courseCode
            it.courseName = course.courseName
            it.student = student
            it.grade = getRandomGrade()
            it.timeSpentOnExamHrs = faker.number().randomDouble(1, 0, 4)
            it.timeSpentOnCourseHrs = faker.random().nextInt(10, 200)
        }

        student.examResults.add(examResult)

        examResultRepository.save(examResult)

        return examResult
    }

    fun getAll(): MutableIterable<Student> {
        return studentRepository.findAll()
    }

    fun studentExist(studentId: String) : Boolean {
        return studentRepository.existsById(studentId)
    }

    fun getExamResults(studentId: String): List<ExamResult> {
       return examResultRepository.findAllByStudentStudentId(studentId)
    }

    fun deleteAll() {
        studentRepository.deleteAll()
    }


}