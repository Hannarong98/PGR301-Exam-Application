package no.kristiania.pgr301.exam

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import no.kristiania.pgr301.exam.dto.CourseDto
import no.kristiania.pgr301.exam.repository.CourseRepository
import no.kristiania.pgr301.exam.repository.ExamResultRepository
import no.kristiania.pgr301.exam.repository.StudentRepository
import no.kristiania.pgr301.exam.service.CourseService
import no.kristiania.pgr301.exam.service.StudentService
import no.kristiania.pgr301.exam.service.UserService
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Profile
import javax.annotation.PostConstruct

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
class StudentRestApiTest{

    @LocalServerPort
    protected var port = 0

    @Autowired
    protected lateinit var userService: UserService

    @Autowired
    protected lateinit var studentService: StudentService

    @Autowired
    protected lateinit var courseService: CourseService


    @Autowired
    protected lateinit var examResultRepository: ExamResultRepository

    @PostConstruct
    fun init(){
        RestAssured.baseURI = "http://localhost"
        RestAssured.basePath = "/api"
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @BeforeEach
    fun cleanUp(){
        examResultRepository.deleteAll()
        studentService.deleteAll()
        userService.deleteAll()
        courseService.deleteAll()
    }

    @Test
    fun shouldCreateCourse() {

        val dto = CourseDto("T01", "TEST1")

        given().contentType(ContentType.JSON)
                .body(dto)
                .post("/courses")
                .then()
                .statusCode(201)

        given().get("/courses")
                .then()
                .statusCode(200)
                .assertThat()
                .body("data.list.size", greaterThan(0))
    }




}