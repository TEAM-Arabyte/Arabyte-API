package com.arabyte.arabyteapi.domain.comment.dto

data class CreateCommentRequest(
    val articleId: Long,
    val text: String,
    val parentId: Long?,
    val isAnonymous: Boolean
)