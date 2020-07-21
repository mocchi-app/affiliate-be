package org.mocchi.affiliate.client

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import org.mocchi.affiliate.model.client.PasswordlessConfiguration
import org.mocchi.affiliate.model.client.PasswordlessStartResponse
import org.mocchi.affiliate.model.client.TokenRequestBody
import org.mocchi.affiliate.model.client.TokenResponse
import org.springframework.stereotype.Component

@Component
class PasswordlessLoginClient(
    private val auth0Client: HttpClient
) {

    companion object {
        const val PASSWORD_LESS_START = "/passwordless/start"
        const val TOKEN_URL = "/oauth/token"
    }

    suspend fun passwordlessStart(
        url: String,
        passwordlessConfiguration: PasswordlessConfiguration
    ): PasswordlessStartResponse =
        auth0Client.post(PASSWORD_LESS_START) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            url {
                host = url
                protocol = URLProtocol.HTTPS
            }
            body = passwordlessConfiguration
        }

    suspend fun getToken(
        url: String,
        tokenRequestBody: TokenRequestBody
    ): TokenResponse =
        auth0Client.post(TOKEN_URL) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            url {
                host = url
                protocol = URLProtocol.HTTPS
            }
            body = tokenRequestBody
        }
}
