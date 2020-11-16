package no.kristiania.pgr301.exam.controller

import no.kristiania.pgr301.exam.converter.DtoConvertExamResult
import no.kristiania.pgr301.exam.converter.DtoConverterStudent
import no.kristiania.pgr301.exam.dto.ExamResultDto
import no.kristiania.pgr301.exam.dto.SignUpDto
import no.kristiania.pgr301.exam.dto.StudentDto
import no.kristiania.pgr301.exam.service.StudentService
import no.kristiania.pgr301.exam.service.UserService
import no.kristiania.pgr301.exam.util.RestResponseFactory
import no.kristiania.pgr301.exam.util.WrappedResponse
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
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
        private val studentService: StudentService
) {

    @GetMapping(path = ["/{studentId}"])
    fun getStudent(
            @PathVariable("studentId")
            studentId: String
    ) : ResponseEntity<WrappedResponse<StudentDto>> {
        val student = studentService.findByIdEager(studentId) ?: return ResponseEntity.notFound().build()
        return RestResponseFactory.payload(200, DtoConverterStudent.transform(student))
    }

    @PostMapping(path = ["/signup"], consumes = [(MediaType.APPLICATION_JSON_VALUE)])
    fun signup(@RequestBody dto: SignUpDto) : ResponseEntity<WrappedResponse<Void>>{

        val studentId = dto.studentId!!
        val registered = userService.createUser(studentId)

        if(!registered){
            return RestResponseFactory.userFailure("Id already existed", 400)
        }

        registerStudentToDashBoard(studentId)

        return RestResponseFactory.noPayload(201)
    }

    fun registerStudentToDashBoard(studentId: String) {
       studentService.registerStudent(studentId)
    }

    @PutMapping(path = ["/{studentId}/exams/{courseCode}"])
    fun doExam(
            @PathVariable("studentId")
            studentId: String,
            @PathVariable
            courseCode: String
    ) : ResponseEntity<WrappedResponse<ExamResultDto>>{
        val examResult = studentService.takeExam(studentId, courseCode)

        return if(examResult == null) {
            RestResponseFactory.userFailure("Student had already taken $courseCode exam", 400)
        } else {
            RestResponseFactory.payload(200, DtoConvertExamResult.transform(examResult))
        }
    }

    @GetMapping(path = ["/{studentId}/exams/"])
    fun getExamResults(
            @PathVariable("studentId")
            studentId: String
    ) : ResponseEntity<WrappedResponse<List<ExamResultDto>>> {
        val results = studentService.getExamResults(studentId)

        return RestResponseFactory.payload(200, DtoConvertExamResult.transform(results))
    }


}