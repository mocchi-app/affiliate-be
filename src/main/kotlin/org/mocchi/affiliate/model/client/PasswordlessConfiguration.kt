package org.mocchi.affiliate.model.client

import com.fasterxml.jackson.annotation.JsonProperty

data class PasswordlessConfiguration(
    @JsonProperty("client_id")
    val clientId: String,
    @JsonProperty("client_secret")
    val clientSecret: String,
    val connection: String,
    val email: String,
    val send: String,
    val authParams: AuthParams
)

data class AuthParams(
    val scope: String = "code"
)
