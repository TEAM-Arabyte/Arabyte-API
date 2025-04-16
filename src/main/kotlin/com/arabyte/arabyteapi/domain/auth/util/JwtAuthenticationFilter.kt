package com.arabyte.arabyteapi.domain.auth.util

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = jwtProvider.resolveToken(request)

        if (!token.isNullOrBlank() && jwtProvider.isValidToken(token)) {
            val userId = jwtProvider.getUserId(token)
            val userDetails =
                User.builder()
                    .username(userId)
                    .password("")
                    .authorities(emptyList())
                    .build()
            val authentication =
                UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}