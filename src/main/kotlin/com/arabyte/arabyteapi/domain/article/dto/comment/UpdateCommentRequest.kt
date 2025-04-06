package com.arabyte.arabyteapi.domain.article.dto.comment

data class UpdateCommentRequest(
    val text: String,
    val isAnonymous: Boolean
)