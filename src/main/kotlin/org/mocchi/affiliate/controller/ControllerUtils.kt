package org.mocchi.affiliate.controller

import kotlinx.coroutines.reactive.awaitFirst
import org.mocchi.affiliate.controller.exception.ResourceNotFoundException
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt

suspend fun getEmailFromContext() =
    ReactiveSecurityContextHolder.getContext()
        .awaitFirst()
        .authentication
        .principal
        .takeIf { it is Jwt }
        ?.let { it as Jwt }
        ?.let { it.claims["name"] as String }
        ?: throw ResourceNotFoundException("user doesn't exist")
