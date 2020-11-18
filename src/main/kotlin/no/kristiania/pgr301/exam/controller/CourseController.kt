package no.kristiania.pgr301.exam.controller

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Tags
import io.micrometer.core.instrument.Timer
import no.kristiania.pgr301.exam.converter.DtoConverterCourse
import no.kristiania.pgr301.exam.dto.CourseDto
import no.kristiania.pgr301.exam.repository.CourseRepository
import no.kristiania.pgr301.exam.service.CourseService
import no.kristiania.pgr301.exam.util.RestResponseFactory
import no.kristiania.pgr301.exam.util.WrappedResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/courses"])
class CourseController(
        private val courseRepository: CourseRepository,
        private val courseService: CourseService,
        private val meterRegistry: MeterRegistry
) {


    @GetMapping
    fun getCourses() : ResponseEntity<WrappedResponse<List<CourseDto>>> {
        val courses = courseRepository.findAll()

        meterRegistry.gaugeCollectionSize("courses_total", Tags.empty(), courses.toList())


        return RestResponseFactory.payload(200, DtoConverterCourse.transform(courses))
    }

    @GetMapping(path = ["/{courseCode}"])
    fun getCourse(
            @PathVariable("courseCode")
            courseCode: String
    ): ResponseEntity<WrappedResponse<CourseDto>> {


        val course = courseRepository.findByCourseCode(courseCode) ?: return RestResponseFactory.notFound("Course not found")

        return RestResponseFactory.payload(200, DtoConverterCourse.transform(course))
    }

    @PostMapping
    fun createCourse(
            @RequestBody
            dto: CourseDto
    ) : ResponseEntity<WrappedResponse<CourseDto>> {
        val course = courseService.createCourse(dto.courseCode!!, dto.courseName!!)
                ?: return RestResponseFactory.userFailure("Could not create this course", 400)

        return RestResponseFactory.payload(201, DtoConverterCourse.transform(course))
    }
}