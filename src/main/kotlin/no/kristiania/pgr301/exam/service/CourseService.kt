package no.kristiania.pgr301.exam.service

import no.kristiania.pgr301.exam.entity.Course
import no.kristiania.pgr301.exam.repository.CourseRepository
import org.springframework.stereotype.Service

@Service
class CourseService (
        private val courseRepository: CourseRepository
) {

    fun createCourse(courseCode: String, courseName: String): Course?{
        if(courseRepository.existsById(courseCode)){
            return null
        }

        val course = Course()
        course.apply {
            this.courseCode = courseCode
            this.courseName = courseName
        }

        courseRepository.save(course)
        return course
    }

    fun deleteAll() {
        courseRepository.deleteAll()
    }
}