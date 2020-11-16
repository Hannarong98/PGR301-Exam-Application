package no.kristiania.pgr301.exam.repository

import no.kristiania.pgr301.exam.db.Course
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ExamRepository : CrudRepository<Course, Long>