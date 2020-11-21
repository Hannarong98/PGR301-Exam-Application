package no.kristiania.pgr301.exam

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    // "--spring.profiles.active=prod" for production send logs to logz.io
    // "--spring.profiles.active=dev" for development show logs on stdout
    // When running on 'dev' profile and you want to get metrics to be delivered to influxdb
    // Change this line in application.properties '....influx.enabled=false' in  to '....influx.enabled=true'
    SpringApplication.run(Application::class.java, "--spring.profiles.active=prod")
}