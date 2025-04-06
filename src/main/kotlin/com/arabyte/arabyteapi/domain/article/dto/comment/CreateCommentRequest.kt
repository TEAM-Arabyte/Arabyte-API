package com.arabyte.arabyteapi.domain.article.dto.comment

data class CreateCommentRequest(
    val articleId: Long,
    val userId: Long,
    val text: String,
    val parentId: Long? = null,
    val isAnonymous: Boolean
)