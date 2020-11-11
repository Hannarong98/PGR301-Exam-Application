package no.kristiania.pgr301.exam

import com.github.javafaker.Faker
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import no.kristiania.pgr301.exam.dto.QuoteDto
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import no.kristiania.pgr301.exam.repository.OriginatorRepository
import no.kristiania.pgr301.exam.repository.QuoteRepository
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
class RestApiTest{

    @LocalServerPort
    protected var port = 0

    @Autowired
    protected lateinit var originatorRepository : OriginatorRepository

    @Autowired
    protected lateinit var quoteRepository: QuoteRepository



    @PostConstruct
    fun init(){
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @BeforeEach
    fun cleanUp(){
        quoteRepository.deleteAll()
        originatorRepository.deleteAll()
    }


    @Test
    fun testCreateOriginator(){
        given().post("/originators")
                .then()
                .statusCode(201)

        given().get("/originators")
                .then()
                .statusCode(200)
                .body("data.originators.size", equalTo(1))
    }

    @Test
    fun testCreateQuote(){

        val originatorId = given().contentType(ContentType.JSON)
                        .post("/originators")
                        .then()
                        .statusCode(201)
                        .extract()
                        .jsonPath().getLong("id")

        given().contentType(ContentType.JSON)
                .post("/originators/$originatorId/quotes")
                .then()
                .statusCode(201)

        given().get("/originators/$originatorId/quotes")
                .then()
                .statusCode(200)
                .body("data.originators.size", greaterThan(0))

        assertTrue(quoteRepository.findByOriginatorId(originatorId).isNotEmpty())
    }


    @Test
    fun testNotFoundWhenOriginatorIsNotPresent(){
        given().contentType(ContentType.JSON)
                .post("/originators/1/quotes")
                .then()
                .statusCode(404)
    }




}