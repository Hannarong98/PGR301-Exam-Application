package no.kristiania.pgr301.exam.service

import no.kristiania.pgr301.exam.entity.User
import no.kristiania.pgr301.exam.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService (
        private val userRepository: UserRepository
) {
    fun createUser(studentId: String): Boolean{


        try {
            if(userRepository.existsById(studentId)){
                return false
            }
            val user = User(studentId)

            userRepository.save(user)

            return true
        } catch (e: Exception){
            return false
        }
    }

    fun deleteAll() {
        userRepository.deleteAll()
    }
}