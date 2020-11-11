package no.kristiania.pgr301.exam.controller


import com.github.javafaker.Faker
import no.kristiania.pgr301.exam.conveter.DtoConverterQuote
import no.kristiania.pgr301.exam.db.Quote
import no.kristiania.pgr301.exam.dto.QuoteDto
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import no.kristiania.pgr301.exam.repository.OriginatorRepository
import no.kristiania.pgr301.exam.repository.QuoteRepository
import no.kristiania.pgr301.exam.util.WrappedResponse
import org.springframework.stereotype.Controller
import java.net.URI

@RestController
@RequestMapping(
        path = ["/originators"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)]
)
class QuotesController (
        private val originatorRepository: OriginatorRepository,
        private val quoteRepository: QuoteRepository
) {


    private val faker = Faker()

    @PostMapping("/{originatorId}/quotes")
    fun createQuote(
            @PathVariable("originatorId") originatorId: Long
    ) : ResponseEntity <QuoteDto> {

        val originator = originatorRepository.findById(originatorId)
                .orElse(null) ?: return ResponseEntity.notFound().build<QuoteDto>()

        val randoms = (0..5).random()

        val randomsYear = (1300..2020).random()

        val createdQuote = when(randoms){
            1 -> Quote(faker.hitchhikersGuideToTheGalaxy().quote(), randomsYear, originator)
            2 -> Quote(faker.chuckNorris().fact(), randomsYear, originator)
            3 ->  Quote(faker.shakespeare().hamletQuote(), randomsYear, originator)
            else ->  Quote(faker.rickAndMorty().quote(), randomsYear, originator)
        }

        val saved = quoteRepository.save(createdQuote)

        return ResponseEntity.created(URI.create("/${originatorId}/quotes")).body(QuoteDto(saved.quote, saved.year, saved.id))
    }

    @GetMapping("/{originatorId}/quotes")
    fun getAllQuotesFromOriginator(
            @PathVariable("originatorId") originatorId: Long
    ) : ResponseEntity<WrappedResponse<List<QuoteDto>>> {

        if(!originatorRepository.existsById(originatorId)) return ResponseEntity.notFound().build()

        val quotes = DtoConverterQuote.transform(quoteRepository.findByOriginatorId(originatorId))

        return ResponseEntity.ok(WrappedResponse(200, quotes).validated())
    }



}