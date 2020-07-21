package org.mocchi.affiliate.converter

import org.mocchi.affiliate.model.entity.UserProfile
import org.springframework.stereotype.Component

@Component
class UserProfileConverter : Converter<Map<String, Any>, UserProfile> {
    override fun convert(source: Map<String, Any>): UserProfile = UserProfile(
        id = extractFromResultSet(source, "up_id"),
        email = extractFromResultSet(source, "email"),
        image = extractFromResultSet(source, "image"),
        stripeAccountId = extractFromResultSet(source, "stripe_account_id")
    )
}
