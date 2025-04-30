package com.arabyte.arabyteapi.domain.comment.dto

import com.arabyte.arabyteapi.domain.comment.entity.Comment

data class UpdateCommentResponse(
    val commentId: Long,
) {
    companion object {
        fun of(comment: Comment): UpdateCommentResponse {
            return UpdateCommentResponse(
                commentId = comment.id
            )
        }
    }
}