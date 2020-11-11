package no.kristiania.pgr301.exam.conveter

import no.kristiania.pgr301.exam.db.Originator
import no.kristiania.pgr301.exam.db.Quote
import no.kristiania.pgr301.exam.dto.OriginatorDto
import no.kristiania.pgr301.exam.dto.QuoteDto

object DtoConverterOriginator {

    fun transform(originator: Originator) : OriginatorDto = originator.run { OriginatorDto(fullName) }

    fun transform(originators: Iterable<Originator>) : List<OriginatorDto> = originators.map { transform(it) }

}

object DtoConverterQuote {

    fun transform(quote: Quote) : QuoteDto = quote.run { QuoteDto(this.quote, year) }

    fun transform(quotes: Iterable<Quote>) : List<QuoteDto> = quotes.map { transform(it) }

}
