package com.arabyte.arabyteapi.domain.auth.dto

data class NickNameDuplicateResponse(
    val isDuplicate: Boolean,
    val massage: String
)