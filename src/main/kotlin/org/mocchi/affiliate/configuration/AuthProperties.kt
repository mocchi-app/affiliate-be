package org.mocchi.affiliate.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
@Component
@ConfigurationProperties(prefix = "auth0")
class AuthProperties {

    @NotBlank
    lateinit var domain: String

    @NotBlank
    lateinit var clientId: String

    @NotBlank
    lateinit var clientSecret: String
}
