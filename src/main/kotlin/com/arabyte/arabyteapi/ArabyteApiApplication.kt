package com.arabyte.arabyteapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class ArabyteApiApplication

fun main(args: Array<String>) {
    runApplication<ArabyteApiApplication>(*args)
}
