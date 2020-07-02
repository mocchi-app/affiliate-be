package org.mocchi.affiliate.model.client

import com.fasterxml.jackson.annotation.JsonProperty

data class PasswordlessStartResponse(
    @JsonProperty("_id")
    val id: String,
    val email: String,
    @JsonProperty("email_verified")
    val emailVerified: Boolean
)
