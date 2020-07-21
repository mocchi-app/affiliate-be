package org.mocchi.affiliate.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Configuration
class StripeConfiguration

@Validated
@Component
@ConfigurationProperties(prefix = "stripe")
class StripeProperties {

    @NotBlank
    lateinit var apiKey: String

    @NotBlank
    lateinit var clientId: String
}
