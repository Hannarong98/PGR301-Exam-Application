package no.kristiania.pgr301.exam.controller

import io.micrometer.core.instrument.DistributionSummary
import io.micrometer.core.instrument.LongTaskTimer
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import no.kristiania.pgr301.exam.converter.DtoConvertExamResult
import no.kristiania.pgr301.exam.converter.DtoConverterStudent
import no.kristiania.pgr301.exam.dto.ExamResultDto
import no.kristiania.pgr301.exam.dto.SignUpDto
import no.kristiania.pgr301.exam.dto.StudentDto
import no.kristiania.pgr301.exam.entity.Student
import no.kristiania.pgr301.exam.service.StudentService
import no.kristiania.pgr301.exam.service.UserService
import no.kristiania.pgr301.exam.util.RestResponseFactory
import no.kristiania.pgr301.exam.util.WrappedResponse
import org.slf4j.LoggerFactory
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

    companion object {
        private val logger = LoggerFactory.getLogger(StudentController::class.java)
    }

    @GetMapping(path = ["/"])
    fun getStudents(
    ): ResponseEntity<WrappedResponse<List<StudentDto>>> {

        var students: MutableIterable<Student> = mutableListOf()


        LongTaskTimer.builder("api_response_long_task")
                .register(meterRegistry)
                .recordCallable {
                    //getAll() have Thread.sleep function before return
                    students = studentService.getAll()
                }

        meterRegistry.gauge("students_age_average", students.map { it.age }.average())

        return RestResponseFactory.payload(200, DtoConverterStudent.transform(students))
    }

    @GetMapping(path = ["/{studentId}"])
    fun getStudent(
            @PathVariable("studentId")
            studentId: String
    ): ResponseEntity<WrappedResponse<StudentDto>> {

        val timer = Timer.start(meterRegistry)
        val student = studentService.findByIdEager(studentId)

        val responseStatus = when (studentService.studentExist(studentId)) {
            true -> 200
            false -> 404
        }

        logger.debug("Returning $responseStatus")

        timer.stop(meterRegistry.timer("api_response", "/api/students/${studentId}", responseStatus.toString()))

        if (student == null) {
            return ResponseEntity.notFound().build()
        }

        return RestResponseFactory.payload(200, DtoConverterStudent.transform(student))
    }

    @PostMapping(path = ["/signup"], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun signup(@RequestBody dto: SignUpDto): ResponseEntity<WrappedResponse<Void>> {

        val studentId = dto.studentId!!
        val registered = userService.createUser(studentId)

        if (!registered) {
            meterRegistry.counter("api_response", "uri", "/api/students/signup", "response_code", "400").increment()
            logger.warn("Could not create user with id: $studentId this id already exist")
            return RestResponseFactory.userFailure("Id already existed", 400)
        }

        registerStudentToDashBoard(studentId)

        meterRegistry.counter("api_response", "uri", "/api/students/signup", "response_code", "201").increment()

        logger.info("Successfully signed up student to dashboard")

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
        val examResult = studentService.submitExam(studentId, courseCode)

        return if (examResult == null) {
            logger.warn("Student $studentId has already taken $courseCode exam")
            RestResponseFactory.userFailure("Student had already taken $courseCode exam", 400)
        } else {

            DistributionSummary.builder("exam_completion")
                    .tag("type", "attempts_taken")
                    .minimumExpectedValue(1)
                    .maximumExpectedValue(4)
                    .register(meterRegistry)
                    .record(examResult.attempts!!.toDouble())

            logger.debug("Course code $courseCode")
            logger.info("A student submitted $courseCode exam")
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