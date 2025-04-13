package com.arabyte.arabyteapi.domain.auth.util

import com.arabyte.arabyteapi.global.exception.CustomException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
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
        val uri = request.requestURI
        if (uri.startsWith("/auth/kakao")) {
            filterChain.doFilter(request, response)
            return
        }


        val token = jwtProvider.resolveToken(request)

        try {
            if (!token.isNullOrBlank() && jwtProvider.isValidToken(token)) {
                val userId = jwtProvider.getUserId(token)
                val userDetails: UserDetails =
                    User.withUsername(userId).password("").authorities(emptyList()).build()
                val authentication = JwtAuthenticationToken(userDetails, token, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authentication
            }

            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
            response.status = e.customError.status.value()
            response.contentType = "application/json;charset=UTF-8"
            val errorJson = """
            {
              "code": "${e.customError.name}",
              "message": "${e.customError.message}"
            }
        """.trimIndent()
            response.writer.write(errorJson)
            response.writer.flush()
        }

    }
}