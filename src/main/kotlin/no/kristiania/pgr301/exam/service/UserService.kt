package no.kristiania.pgr301.exam.service

import no.kristiania.pgr301.exam.entity.User
import no.kristiania.pgr301.exam.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService (
        private val userRepository: UserRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(UserService::class.java)
    }

    fun createUser(studentId: String): Boolean{


        try {
            if(userRepository.existsById(studentId)){
                return false
            }
            val user = User(studentId)

            userRepository.save(user)

            return true
        } catch (e: Exception){
            logger.error("An exception where thrown when trying to create a user ex: $e")
            return false
        }
    }

    fun deleteAll() {
        userRepository.deleteAll()
    }
}