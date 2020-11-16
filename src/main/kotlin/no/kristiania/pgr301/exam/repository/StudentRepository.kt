package no.kristiania.pgr301.exam.repository

import no.kristiania.pgr301.exam.db.Student
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DeveloperRepository : CrudRepository<Student, Long>