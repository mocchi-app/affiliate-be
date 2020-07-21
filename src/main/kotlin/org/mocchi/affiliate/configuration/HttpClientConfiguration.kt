package org.mocchi.affiliate.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.CIOEngineConfig
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HttpClientConfiguration {

    companion object {
        const val CONNECT_STRIPE_API_BASE = "connect.stripe.com"
    }

    @Bean
    fun auth0Client() = HttpClient(CIO) {
        installBasicHttpClient()
    }

    @Bean
    fun stripeHttpClient() = HttpClient(CIO) {
        engine {
            https {
                serverName = CONNECT_STRIPE_API_BASE
            }
        }
        installBasicHttpClient()
    }

    private fun HttpClientConfig<CIOEngineConfig>.installBasicHttpClient() {
        install(JsonFeature) {
            serializer = JacksonSerializer(
                ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .registerModule(KotlinModule())
                    .also { it.findAndRegisterModules() }
            )
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
    }
}
