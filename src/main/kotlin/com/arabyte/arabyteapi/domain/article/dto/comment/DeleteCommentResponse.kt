package com.arabyte.arabyteapi.domain.article.dto.comment

data class DeleteCommentResponse(
    val commentId: Long,
    val message: String
)