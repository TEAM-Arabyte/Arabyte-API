package com.arabyte.arabyteapi

import com.arabyte.arabyteapi.auth.util.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfiguration(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    @Bean
    @Profile("local")
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                ////it.requestMatchers("/api/auth/**").permitAll()
                ////it.anyRequest().authenticated()
                it.anyRequest().permitAll()
                //it.requestMatchers("/api/auth/**").permitAll() // 인증 없이 접근 가능
                //it.anyRequest().authenticated() // 나머지는 인증 필요
            }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
        //.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}