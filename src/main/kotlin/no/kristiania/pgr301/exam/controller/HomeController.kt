package no.kristiania.pgr301.exam.controller

import no.kristiania.pgr301.exam.util.RestResponseFactory
import no.kristiania.pgr301.exam.util.WrappedResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// Pinged by statuscake
@RestController
@RequestMapping(path = ["/"])
class HomeController {
    @GetMapping
    fun home() : ResponseEntity<WrappedResponse<Void>> {
        return RestResponseFactory.noPayload(200)
    }
}