package com.arabyte.arabyteapi.domain.comment.dto

data class CommentResponse(
    val commentId: Long,
    val userId: Long,
    val text: String,
    val nickname: String,
    val createdAt: String,
    val isAnonymous: Boolean,
    val parentId: Long? = null
)
