package org.mocchi.affiliate.controller

import org.mocchi.affiliate.model.client.PasswordlessStartResponse
import org.mocchi.affiliate.model.dto.PasswordlessDto
import org.mocchi.affiliate.service.Auth0Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val auth0Service: Auth0Service
) {

    @PostMapping("/passwordless/start")
    suspend fun login(
        @RequestBody passwordlessDto: PasswordlessDto
    ): PasswordlessStartResponse =
        auth0Service.startPasswordlessVerification(passwordlessDto)
}
