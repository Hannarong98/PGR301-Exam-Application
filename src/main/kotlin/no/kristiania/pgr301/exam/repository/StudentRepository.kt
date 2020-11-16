package no.kristiania.pgr301.exam.repository

import no.kristiania.pgr301.exam.entity.Student
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.persistence.LockModeType

@Repository
interface StudentRepository : CrudRepository<Student, String> {
    fun findByStudentId(studentId: String): Student?
}