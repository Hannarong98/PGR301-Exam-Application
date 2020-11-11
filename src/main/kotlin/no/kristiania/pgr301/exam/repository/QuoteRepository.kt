package no.kristiania.pgr301.exam.repository

import no.kristiania.pgr301.exam.db.Quote
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface QuoteRepository : CrudRepository<Quote, Long> {
   fun findByOriginatorId(originatorId: Long): List<Quote>
}