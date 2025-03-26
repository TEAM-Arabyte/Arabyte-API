package com.arabyte.arabyteapi.domain.article.dto.article

data class UpdateArticleRequest(
    val title: String,
    val text: String,
    val isAnonymous: Boolean
)