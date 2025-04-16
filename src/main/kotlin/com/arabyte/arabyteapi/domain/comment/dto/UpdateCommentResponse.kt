package com.arabyte.arabyteapi.domain.comment.dto

data class UpdateCommentResponse(
    val commentId: Long,
    val message: String
)