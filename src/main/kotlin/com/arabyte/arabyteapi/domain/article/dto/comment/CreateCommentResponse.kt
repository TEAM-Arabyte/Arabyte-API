package com.arabyte.arabyteapi.domain.article.dto.comment

data class CreateCommentResponse(
    val commentId: Long,
    val message: String
)