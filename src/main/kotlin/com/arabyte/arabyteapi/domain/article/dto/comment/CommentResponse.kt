package com.arabyte.arabyteapi.domain.article.dto.comment

data class CommentResponse(
    val commentId: Long,
    val text: String,
    val nickname: String,
    val createdAt: String,
    val isAnonymous: Boolean,
    val parentId: Long? = null
)
