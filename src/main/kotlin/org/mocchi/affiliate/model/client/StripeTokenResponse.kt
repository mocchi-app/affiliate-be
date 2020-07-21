package org.mocchi.affiliate.model.client

import com.fasterxml.jackson.annotation.JsonProperty

data class StripeTokenResponse(
    val livemode: String,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("stripe_user_id")
    val stripeUserId: String,
    val scope: String
)
