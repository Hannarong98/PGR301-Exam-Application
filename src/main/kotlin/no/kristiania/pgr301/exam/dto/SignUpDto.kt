package no.kristiania.pgr301.exam.dto

import javax.validation.constraints.NotBlank

class SignUpDto(
        @get:NotBlank
        var studentId: String? = null
)