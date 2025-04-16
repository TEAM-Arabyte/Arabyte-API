package com.arabyte.arabyteapi.domain.comment.dto

data class CreateCommentRequest(
    val articleId: Long,
    val userId: Long,
    val text: String,
    val parentId: Long?,
    val isAnonymous: Boolean
)