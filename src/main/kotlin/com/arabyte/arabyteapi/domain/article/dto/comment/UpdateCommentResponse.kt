package com.arabyte.arabyteapi.domain.article.dto.comment

data class UpdateCommentResponse(
    val commentId: Long,
    val message: String
)