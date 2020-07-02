package org.mocchi.affiliate.model.dto

data class ConfirmCodeDto(
    val username: String,
    val otp: String,
    val realm: String
)
