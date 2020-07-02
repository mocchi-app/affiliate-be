package org.mocchi.affiliate.cleint

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import org.mocchi.affiliate.model.client.PasswordlessConfiguration
import org.mocchi.affiliate.model.client.PasswordlessStartResponse
import org.springframework.stereotype.Component

@Component
class PasswordlessLoginClient(
    private val auth0Client: HttpClient
) {

    suspend fun passwordlessStart(
        url: String,
        passwordlessConfiguration: PasswordlessConfiguration
    ): PasswordlessStartResponse =
        auth0Client.post("/passwordless/start") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            url {
                host = url
                protocol = URLProtocol.HTTPS
            }
            body = passwordlessConfiguration
        }
}
