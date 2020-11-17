package no.kristiania.pgr301.exam.repository

import no.kristiania.pgr301.exam.entity.Course
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.persistence.LockModeType

@Repository
interface CourseRepository : CrudRepository<Course, String>{
   fun findByCourseCode(courseCode: String):  Course?
}