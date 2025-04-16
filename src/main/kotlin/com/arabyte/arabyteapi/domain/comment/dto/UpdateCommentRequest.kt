package com.arabyte.arabyteapi.domain.comment.dto

data class UpdateCommentRequest(
    val text: String,
    val isAnonymous: Boolean
)