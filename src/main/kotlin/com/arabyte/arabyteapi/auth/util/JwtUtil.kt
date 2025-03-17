package com.arabyte.arabyteapi.auth.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
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

    fun extractUserId(token: String): String? {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
            claims.subject
        } catch (e: Exception) {
            null
        }
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}
