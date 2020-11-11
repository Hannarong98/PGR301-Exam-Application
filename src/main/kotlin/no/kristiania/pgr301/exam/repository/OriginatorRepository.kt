package no.kristiania.pgr301.exam.repository

import no.kristiania.pgr301.exam.db.Originator
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OriginatorRepository : CrudRepository<Originator, Long>