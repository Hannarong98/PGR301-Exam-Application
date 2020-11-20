package no.kristiania.pgr301.exam.service
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Service
@Transactional
class FakeDataService (
        private val courseService: CourseService,
        private val studentService: StudentService
) {

    companion object {
        private val logger = LoggerFactory.getLogger(FakeDataService::class.java.simpleName)
    }

    @PostConstruct
    fun init(){
        courseService.run {
            createCourse("1001", "Digital teknologi")
            createCourse("1002", "Kreativt webprosjekt")
            createCourse("1003", "Introduksjon til programmering")
            createCourse("1004", "Databaser 1")
            createCourse("2001", "Informasjonssikkerhet")
            createCourse("2002", "Webprosjekt")
            createCourse("2003", "Objektorientert programmering")
            createCourse("2004", "Avansert Javaprogrammering")
            createCourse("2005", "Algoritmer og datastrukturer")
            createCourse("2006", "Software Design")
            createCourse("2008", "Smidig prosjekt")
            createCourse("2009", "Android programmering")
            createCourse("3001", "Enterpriseprogrammering 1")
            createCourse("3002", "Enterpriseprogrammering 2")
            createCourse("3004", "Programmering i C for Linux")
            createCourse("3005", "DevOps i skyen")
            createCourse("3006", "Embedded systems")
        }
        logger.info("FakeDataService finished registering courses");

        studentService.run {
            registerStudent("benost20")
            registerStudent("andarc20")
            registerStudent("glebch20")
            registerStudent("tomsan20")
            registerStudent("johbro20")
            registerStudent("eivbre20")
            registerStudent("rolgon20")
        }

        logger.info("FakeDataService finished registering students");
    }

}