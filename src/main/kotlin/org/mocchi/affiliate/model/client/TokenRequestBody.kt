package org.mocchi.affiliate.model.client

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenRequestBody(
    @JsonProperty("grant_type")
    val grantType: String,
    @JsonProperty("client_id")
    val clientId: String,
    @JsonProperty("client_secret")
    val clientSecret: String,
    @JsonProperty("username")
    val username: String,
    @JsonProperty("otp")
    val otp: String,
    @JsonProperty("realm")
    val realm: String,
    @JsonProperty("scope")
    val scope: String
)
