package org.mocchi.affiliate.client

import com.stripe.model.oauth.TokenResponse
import com.stripe.net.OAuth
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.URLProtocol
import org.mocchi.affiliate.configuration.HttpClientConfiguration
import org.mocchi.affiliate.configuration.StripeProperties
import org.mocchi.affiliate.model.client.StripeTokenResponse
import org.springframework.stereotype.Component

@Component
class StripeClient(
    private val stripeHttpClient: HttpClient,
    private val stripeProperties: StripeProperties
) {

    suspend fun getToken(code: String): StripeTokenResponse =
        stripeHttpClient.post("/oauth/token") {
            url {
                host = HttpClientConfiguration.CONNECT_STRIPE_API_BASE
                protocol = URLProtocol.HTTPS
            }
            header("Authorization", "Bearer ${stripeProperties.apiKey}")
            parameter("code", code)
            parameter("grant_type", "authorization_code")
        }
}
