package com.arabyte.arabyteapi.domain.article.dto.article

import com.arabyte.arabyteapi.domain.article.enums.ArticleKind

data class ArticlePreviewResponse(
    val title: String,
    val text: String,
    val likeCount: Int,
    val commentCount: Int,
    val uploadAt: String,
    val thumbnailImage: String?,
    val articleKind: ArticleKind
)
