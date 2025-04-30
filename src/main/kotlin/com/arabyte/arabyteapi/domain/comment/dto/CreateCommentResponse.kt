package com.arabyte.arabyteapi.domain.comment.dto

import com.arabyte.arabyteapi.domain.comment.entity.Comment

data class CreateCommentResponse(
    val commentId: Long,
) {
    companion object {
        fun of(comment: Comment): CreateCommentResponse {
            return CreateCommentResponse(
                commentId = comment.id
            )
        }
    }
}