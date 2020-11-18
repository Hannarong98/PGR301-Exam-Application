package no.kristiania.pgr301.exam.converter

import no.kristiania.pgr301.exam.entity.Course
import no.kristiania.pgr301.exam.dto.CourseDto

object DtoConverterCourse {
    fun transform(course: Course) : CourseDto {
        return CourseDto().apply {
            courseCode = course.courseCode
            courseName = course.courseName
        }
    }

    fun transform(courses: Iterable<Course>) : List<CourseDto> = courses.map { transform(it) }
}