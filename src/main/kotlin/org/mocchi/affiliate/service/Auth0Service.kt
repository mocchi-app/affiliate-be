package org.mocchi.affiliate.service

import org.mocchi.affiliate.cleint.PasswordlessLoginClient
import org.mocchi.affiliate.configuration.AuthProperties
import org.mocchi.affiliate.model.client.AuthParams
import org.mocchi.affiliate.model.client.PasswordlessConfiguration
import org.mocchi.affiliate.model.dto.PasswordlessDto
import org.springframework.stereotype.Service

@Service
class Auth0Service(
    private val authProperties: AuthProperties,
    private val passwordlessLoginClient: PasswordlessLoginClient
) {

    suspend fun startPasswordlessVerification(passwordlessDto: PasswordlessDto) =
        passwordlessLoginClient.passwordlessStart(
            authProperties.domain,
            PasswordlessConfiguration(
                clientId = authProperties.clientId,
                clientSecret = authProperties.clientSecret,
                connection = passwordlessDto.connection,
                email = passwordlessDto.email,
                send = passwordlessDto.send,
                authParams = AuthParams(
                    scope = "openid"
                )
            )
        )

}
