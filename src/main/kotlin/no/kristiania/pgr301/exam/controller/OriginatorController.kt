package no.kristiania.pgr301.exam.controller

import com.github.javafaker.Faker
import no.kristiania.pgr301.exam.conveter.DtoConverterOriginator
import no.kristiania.pgr301.exam.db.Originator
import no.kristiania.pgr301.exam.dto.OriginatorDto
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import no.kristiania.pgr301.exam.repository.OriginatorRepository
import no.kristiania.pgr301.exam.util.WrappedResponse
import java.net.URI

@RestController
class OriginatorController (
        private val originatorRepository: OriginatorRepository
) {

    val faker = Faker()

    @GetMapping("/originators")
    fun getOriginators() : ResponseEntity<WrappedResponse<List<OriginatorDto>>> {
        val allOriginator = DtoConverterOriginator.transform(originatorRepository.findAll())

        return ResponseEntity
                .status(200)
                .body(WrappedResponse(200, allOriginator).validated())
    }

    @PostMapping("/originators", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createOriginator(): ResponseEntity<OriginatorDto> {

        val originator = when((0..5).random()){
            1 -> Originator(faker.elderScrolls().firstName() + " " + faker.elderScrolls().lastName())
            2 -> Originator(faker.funnyName().name())
            3 -> Originator(faker.hitchhikersGuideToTheGalaxy().character())
            else ->  Originator(Faker().superhero().name())
        }

        val created = originatorRepository.save(originator)

       return ResponseEntity
               .created(URI.create("${created.id}"))
               .body(OriginatorDto(created.fullName, created.id))
    }



}