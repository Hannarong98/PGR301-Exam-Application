package no.kristiania.pgr301.exam.converter

import no.kristiania.pgr301.exam.entity.Student
import no.kristiania.pgr301.exam.dto.StudentDto

object DtoConverterStudent {

    fun transform(student: Student) : StudentDto {
        return StudentDto().apply {
            id  = student.studentId
            firstName = student.firstName
            lastName = student.lastName
            age = student.age
            examResults = student.examResults.map {
                DtoConvertExamResult.transform(it)
            }.toMutableList()
        }
    }

    fun transform(students: Iterable<Student>) : List<StudentDto> = students.map { transform(it) }

}
