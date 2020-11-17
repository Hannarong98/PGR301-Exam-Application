package no.kristiania.pgr301.exam.service

import no.kristiania.pgr301.exam.repository.CourseRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Profile("FakeData")
@Service
@Transactional
class FakeDataService (
        private val courseService: CourseService
) {

    @PostConstruct
    fun init(){
        courseService.run {
            createCourse("TK1100", "Digital teknologi")
            createCourse("PRO100", "Kreativt webprosjekt")
            createCourse("PGR102", "Introduksjon til programmering")
            createCourse("DB1100", "Databaser 1")
            createCourse("TK2100", "Informasjonssikkerhet")
            createCourse("PRO101", "Webprosjekt")
            createCourse("PGR103", "Objektorientert programmering")
            createCourse("PGR203", "Avansert Javaprogrammering")
            createCourse("PG4200", "Algoritmer og datastrukturer")
            createCourse("PG3301", "Software Design")
            createCourse("PRO200", "Smidig prosjekt")
            createCourse("PGR208", "Android programmering")
            createCourse("PG5100", "Enterpriseprogrammering 1")
            createCourse("PG6102", "Enterpriseprogrammering 2")
            createCourse("PG3401", "Programmering i C for Linux")
            createCourse("PGR301", "DevOps i skyen")
            createCourse("PG5501", "Embedded systems")
        }
    }

}