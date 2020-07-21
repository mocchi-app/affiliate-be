package org.mocchi.affiliate.converter

import org.mocchi.affiliate.model.entity.UserState
import org.springframework.stereotype.Component

@Component
class UserStateConverter : Converter<Map<String, Any>, UserState> {

    override fun convert(source: Map<String, Any>): UserState =
        UserState(
            id = extractFromResultSet(source, "us_id"),
            userId = extractFromResultSet(source, "us_up_id")
        )
}
