package com.arabyte.arabyteapi.domain.comment.dto

data class CreateCommentResponse(
    val commentId: Long,
    val message: String
)