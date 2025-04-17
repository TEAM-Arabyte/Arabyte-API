package com.arabyte.arabyteapi.domain.auth.util

import com.arabyte.arabyteapi.global.enums.CustomError
import com.arabyte.arabyteapi.global.exception.CustomException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${spring.security.jwt.secret-key}")
    secret: String,

    @Value("\${spring.security.jwt.access-token-expiration-ms}")
    private val expirationMs: Long,

    @Value("\${spring.security.jwt.refresh-token-expiration-ms}")
    private val refreshTokenExpirationMs: Long
) {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray()) // SecretKey 생성

    fun generateAccessToken(userId: String): String {
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateRefreshToken(userId: String): String {
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + refreshTokenExpirationMs))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val rawToken = request.cookies?.find { it.name == HttpHeaders.AUTHORIZATION }?.value
            ?: request.getHeader(HttpHeaders.AUTHORIZATION)
            ?: return null

        return rawToken.replace("Bearer ", "")
    }

    fun isValidToken(token: String): Boolean {
        return try {
            val now = Date()
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            return claims.body.expiration.after(now)
        } catch (e: ExpiredJwtException) {
            throw CustomException(CustomError.ACCESS_TOKEN_EXPIRED)
        } catch (e: Exception) {
            false
        }
    }

    fun getUserId(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }
}
