package org.mocchi.affiliate.model.dto

data class PasswordlessDto(
    val connection: String,
    val email: String,
    val send: String
)
