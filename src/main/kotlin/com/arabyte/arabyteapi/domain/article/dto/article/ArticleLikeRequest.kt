package com.arabyte.arabyteapi.domain.article.dto.article

data class ArticleLikeRequest(
    val articleId: Long,
    val userId: Long,
)
