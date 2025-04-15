package com.arabyte.arabyteapi.domain.user.dto

data class CheckNickNameResponse(
    val isDuplicate: Boolean,
    val massage: String
)