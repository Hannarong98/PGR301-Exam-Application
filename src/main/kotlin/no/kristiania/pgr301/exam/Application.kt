package no.kristiania.pgr301.exam

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    // "--spring.profiles.active=prod" for production ship logs to logz.io
    // "--spring.profiles.active=dev" for development show logs on stdout
    SpringApplication.run(Application::class.java, "--spring.profiles.active=prod")
}