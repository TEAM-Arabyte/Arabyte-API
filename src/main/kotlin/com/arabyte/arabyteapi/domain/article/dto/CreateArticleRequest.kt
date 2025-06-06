package com.arabyte.arabyteapi.domain.article.dto

import com.arabyte.arabyteapi.domain.article.enums.ArticleKind

data class CreateArticleRequest(
    val title: String,
    val text: String,
    val likeCount: Int,
    var isAnonymous: Boolean,
    val articleKind: ArticleKind,
    val articleImages: List<String>?
)