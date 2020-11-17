package no.kristiania.pgr301.exam.repository

import no.kristiania.pgr301.exam.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, String>