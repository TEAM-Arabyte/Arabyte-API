package com.arabyte.arabyteapi.auth.util

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtAuthenticationToken(
    private val principal: UserDetails,
    private val token: String,
    authorities: Collection<GrantedAuthority>
) : AbstractAuthenticationToken(authorities) {

    init {
        isAuthenticated = true
    }

    override fun getCredentials(): Any {
        return token
    }

    override fun getPrincipal(): Any {
        return principal
    }
}
