package org.mocchi.affiliate.model.dto

data class UserProfileResponse(
    val email: String,
    val about: String?,
    val location: String?
)
