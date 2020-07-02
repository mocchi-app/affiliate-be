package org.mocchi.affiliate.configuration

import com.auth0.AuthenticationController
import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import java.io.UnsupportedEncodingException

@EnableWebFluxSecurity
class SecurityConfiguration {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.csrf().disable()
        http
            .httpBasic().disable()
            .formLogin().disable()
            .authorizeExchange()
            .pathMatchers(
                "/passwordless/start"
            ).permitAll()
            .pathMatchers(
                "/v2/api-docs",
                "/v3/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**",
                "/favicon.ico"
            ).permitAll()
            .anyExchange().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt();
        return http.build();
    }

    @Bean
    @Throws(UnsupportedEncodingException::class)
    fun authenticationController(
        authProperties: AuthProperties
    ): AuthenticationController {
        val jwkProvider: JwkProvider = JwkProviderBuilder(authProperties.domain).build()
        return AuthenticationController.newBuilder(
            authProperties.domain,
            authProperties.clientId,
            authProperties.clientSecret
        )
            .withJwkProvider(jwkProvider)
            .build()
    }

}
