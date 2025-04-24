package com.arabyte.arabyteapi.domain.comment.dto

import com.arabyte.arabyteapi.domain.comment.entity.Comment

data class DeleteCommentResponse(
    val commentId: Long,
) {
    companion object {
        fun of(comment: Comment): DeleteCommentResponse {
            return DeleteCommentResponse(
                commentId = comment.id
            )
        }
    }
}