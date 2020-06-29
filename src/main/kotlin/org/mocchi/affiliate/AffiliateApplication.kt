package org.mocchi.affiliate

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AffiliateApplication

fun main(args: Array<String>) {
    SpringApplication.run(AffiliateApplication::class.java, *args)
}
