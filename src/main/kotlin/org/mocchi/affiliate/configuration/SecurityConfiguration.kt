package org.mocchi.affiliate.configuration

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebFluxSecurity
class SecurityConfiguration {

    @Bean
    fun securityWebFilterChain(
            http: ServerHttpSecurity
    ): SecurityWebFilterChain =
            http
                    .httpBasic().disable()
                    .formLogin().disable()
                    .csrf().disable()
                    .logout().disable()
                    .authorizeExchange()
                    .pathMatchers(
                            "/v2/api-docs",
                            "/v3/api-docs",
                            "/configuration/ui",
                            "/swagger-resources/**",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**",
                            "/favicon.ico"
                    ).permitAll()
                    .pathMatchers("/about").permitAll()
                    .anyExchange().authenticated()
                    .and()
                    .build()
}
