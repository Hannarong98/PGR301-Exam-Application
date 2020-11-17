package no.kristiania.pgr301.exam.controller

import com.github.javafaker.Faker
import io.micrometer.core.annotation.Timed
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import no.kristiania.pgr301.exam.converter.DtoConvertExamResult
import no.kristiania.pgr301.exam.converter.DtoConverterStudent
import no.kristiania.pgr301.exam.dto.ExamResultDto
import no.kristiania.pgr301.exam.dto.SignUpDto
import no.kristiania.pgr301.exam.dto.StudentDto
import no.kristiania.pgr301.exam.repository.ExamResultRepository
import no.kristiania.pgr301.exam.service.StudentService
import no.kristiania.pgr301.exam.service.UserService
import no.kristiania.pgr301.exam.util.RestResponseFactory
import no.kristiania.pgr301.exam.util.WrappedResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
        path = ["/api/students"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)]
)
class StudentController(
        private val userService: UserService,
        private val studentService: StudentService,
        private val meterRegistry: MeterRegistry
) {

    private val faker = Faker()


    // TODO
    // Please check this later

    @GetMapping(path = ["/"])
    @Timed("get_all_student", longTask = true)
    fun getStudents(
    ): ResponseEntity<WrappedResponse<List<StudentDto>>> {

        val randomDouble = faker.number().randomDouble(0, 1000, 3000)

        Thread.sleep(randomDouble.toLong())
        // Assumes this is a long running async task
        val students = studentService.getAll()
        meterRegistry.gauge("students_age_by_avg", students.map { it.age }.average())

        return RestResponseFactory.payload(200, DtoConverterStudent.transform(students))
    }

    @GetMapping(path = ["/{studentId}"])
    fun getStudent(
            @PathVariable("studentId")
            studentId: String
    ): ResponseEntity<WrappedResponse<StudentDto>> {

        val timer = Timer.start(meterRegistry)
        val student = studentService.findByIdEager(studentId)

        val responseStatus = when(studentService.studentExist(studentId)){
            true -> 200
            false -> 404
        }

        timer.stop(meterRegistry.timer("response_timer", "get_one_student", responseStatus.toString()))

        if(student == null){
            return ResponseEntity.notFound().build()
        }

        return RestResponseFactory.payload(200, DtoConverterStudent.transform(student))
    }

    @PostMapping(path = ["/signup"], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun signup(@RequestBody dto: SignUpDto): ResponseEntity<WrappedResponse<Void>> {

        val studentId = dto.studentId!!
        val registered = userService.createUser(studentId)

        if (!registered) {
            meterRegistry.counter("api_request", "/api/students/signup", "400").increment()
            return RestResponseFactory.userFailure("Id already existed", 400)
        }

        registerStudentToDashBoard(studentId)

        meterRegistry.counter("api_request", "/api/students/signup", "201").increment()
        return RestResponseFactory.noPayload(201)
    }

    fun registerStudentToDashBoard(studentId: String) {
        studentService.registerStudent(studentId)
    }

    @PutMapping(path = ["/{studentId}/exams/{courseCode}"])
    fun submitExam(
            @PathVariable("studentId")
            studentId: String,
            @PathVariable
            courseCode: String
    ): ResponseEntity<WrappedResponse<ExamResultDto>> {
        val examResult = studentService.takeExam(studentId, courseCode)

        return if (examResult == null) {
            RestResponseFactory.userFailure("Student had already taken $courseCode exam", 400)
        } else {

            meterRegistry.counter("exam_taken",
                    "course_name", examResult.courseName).increment()

            meterRegistry.summary("avg_time_spent_on_exam",
            "course_name", examResult.courseName)
                    .record(examResult.timeSpentOnExam!!.toDouble())

            RestResponseFactory.payload(200, DtoConvertExamResult.transform(examResult))
        }
    }

    @GetMapping(path = ["/{studentId}/exams/"])
    fun getExamResults(
            @PathVariable("studentId")
            studentId: String
    ): ResponseEntity<WrappedResponse<List<ExamResultDto>>> {

        val results = studentService.getExamResults(studentId)

        return RestResponseFactory.payload(200, DtoConvertExamResult.transform(results))
    }


}