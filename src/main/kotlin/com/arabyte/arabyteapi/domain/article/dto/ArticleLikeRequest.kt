package com.arabyte.arabyteapi.domain.article.dto

data class ArticleLikeRequest(
    val articleId: Long,
    val userId: Long,
)
