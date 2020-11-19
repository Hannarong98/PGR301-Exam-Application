package no.kristiania.pgr301.exam

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    // "--spring.profiles.active=prod" for production send logs to logz.io
    // "--spring.profiles.active=dev" for development show logs on stdout
    // when you are running 'dev' profile and you want to get metrics to
    // be submitted to influxdb do change this ....influx.enabled=false in application.properties
    // to ....influx.enabled=true
    SpringApplication.run(Application::class.java, "--spring.profiles.active=prod")
}