package com.arabyte.arabyteapi.domain.comment.dto

data class DeleteCommentResponse(
    val commentId: Long,
    val message: String
)